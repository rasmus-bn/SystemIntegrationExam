const sqlite3 = require('sqlite3').verbose();
const dbName = './db/feedback.db';
// const foodEndpoint = 'http://food-service:5009/food/id/';
const foodEndpoint = 'http://localhost:5009/api/v1/food/'; // dev
const fetch = require('node-fetch');

class DBError extends Error {
    constructor(message) {
        super(message);
        this.message = message;
        this.name = 'DBError';
    }
}
class UserExposedError extends Error {
    constructor(message, exposeToUser=true) {
        super(message);
        this.message = message;
        this.name = 'UserExposedError';
        if (exposeToUser) this.userMessage = message;
    }
}
class NotFoundError extends Error {
    constructor(message) {
        super(message);
        this.message = message;
        this.name = 'NotFoundError';
    }
}

const initialSetup = () => {
    const promise = new Promise((resolve, reject) => {

        connect().then((db) => {
            try {
                db.run(`
                create table if not exists feedback(
                    id integer primary key,
                    foodId integer not null,
                    rating integer not null,
                    description text,
                    gender text,
                    countryName text,
                    age text
                );
                `, [],
                (err) => {
                    if (err) {
                        throw new DBError(`Failed to create table in DB ${dbName}: ${err}`);
                    }
                    console.log("feedback table created");
                    resolve();
                });
            } finally {
                db.close();
            }
        });
    });
    return promise;
};

const checkFoodId = (id) => {
    console.log("Food service");
    return new Promise((resolve, reject) => {
        resolve({ id: id });
        // fetch(foodEndpoint + id).then((res) => res.text().then((body) => {
        //         const jsonBody = JSON.parse(body);
        //         if (jsonBody.id && parseInt(jsonBody.id) === parseInt(id)) {
        //             resolve(jsonBody);
        //         }
        //     })
        // );
    });
};

const connect = () => {
    const promise = new Promise((resolve, reject) => {

        db = new sqlite3.Database(dbName, (err) => {
            if (err) {
                throw new DBError(`Failed to connect to database ${dbName}: ${err}`);
            }
            console.log(`DB connect:`);
            console.log(db);
            resolve(db);
        });
    });

    return promise;
};

const convertToInt = (value, arguName) => {
    const intVal = parseInt(value);
    if (isNaN(intVal)) throw new UserExposedError(`${arguName} must be an int. Actual: ${value}`);
    return intVal;
}

const createFeedback = (rating, foodId, info = {}) => {
    const promise = new Promise((resolve, reject) => {

        rating = convertToInt(rating, 'rating');
        if (rating < 1 || rating > 5) {
            throw new UserExposedError(`rating must be in range 1-5. Actual: ${rating}`);
        }

        foodId = convertToInt(foodId, 'foodId');

        checkFoodId(foodId).then((jsonBody) => {
            console.log(`Food: id=${jsonBody.id}, name=${jsonBody.name}`);
            connect().then((db) => {
                const sql = `
                insert into feedback 
                (foodId, rating, description, gender, countryName, age) 
                values (?, ?, ?, ?, ?, ?);
                `;

                try {
                    db.run(sql, [
                        foodId, 
                        rating, 
                        info.description, 
                        info.gender, 
                        info.countryName, 
                        info.age
                    ], 
                    (err) => {
                        if (err) {
                            throw new DBError(`Failed to create feedback with foodId ${foodId}: ${err}`);
                        }
                        console.log(`Created feedback with foodId ${foodId}`);
                        resolve();
                    })
                }  finally {
                    db.close();
                }
            });
        });

        
    });
    return promise;
};

const readFeedbackById = (id) => {
    const promise = new Promise((resolve, reject) => {

        id = convertToInt(id, 'id');
        const sql = `select * from feedback where id = ?;`;

        connect().then((db) => {
            try {
                db.get(sql, [id], (err, row) => {
                    if (err) {
                        throw new DBError(`Failed to retrieve feedback with id ${id}: ${err}`);
                    }
                    console.log(`Read feedback with id ${id}`);
                    resolve(row);
                });
            } finally {
                db.close();
            }
        });
    });
    return promise;
};

const readFeedbackByFoodId = (foodId) => {
    const promise = new Promise((resolve, reject) => {

        foodId = convertToInt(foodId, 'foodId');
        let feedback = [];
        const sql = `select * from feedback where foodId = ?;`;

        connect().then((db) => {
            try {
                db.all(sql, [foodId], (err, rows) => {
                    if (err) {
                        throw new DBError(`Failed to retrieve feedback with foodId ${foodId}: ${err}`);
                    }
                    console.log(`Read feedback with foodId  ${foodId}`);
                    resolve(rows);
                });
            } finally {
                db.close();
            }
        });
    });
    return promise;
};

const updateFeedback = (id, rating, foodId, info = {}) => {
    const promise = new Promise((resolve, reject) => {

        id = convertToInt(id, 'id');
        rating = convertToInt(rating, 'rating');
        if (rating < 1 || rating > 5) {
            throw new UserExposedError(`rating must be in range 1-5. Actual: ${rating}`);
        }

        foodId = convertToInt(foodId, 'foodId');


        checkFoodId(foodId).then((jsonBody) => {
            console.log(`Food: id=${jsonBody.id}, name=${jsonBody.name}`);
            connect().then((db) => {
                try {
                    const sql = `
                    update feedback 
                        set foodId = ?, rating = ?, description = ?, 
                        gender = ?, countryName = ?, age = ?
                    where id = ?;
                    `;
            
                    db.run(sql, [
                        foodId, rating, info.description, 
                        info.gender, info.countryName, 
                        info.upperAge, id, 
                    ], 
                    (err) => {
                        if (err) {
                            throw new DBError(`Failed to update feedback with id ${id}: ${err}`);
                        }
                        console.log(`Deleted feedback with id  ${id}`);
                        resolve();
                    });
                } finally {
                    db.close();
                }
            });
        });
    });
    return promise;
}

const deleteFeedback = (id) => {
    const promise = new Promise((resolve, reject) => {

        id = convertToInt(id, 'id');
        const sql = `delete from feedback where id = ?;`;

        connect().then((db) => {
            try {
                db.run(sql, [id], (err) => {
                    if (err) {
                        throw new DBError(`Failed to delete feedback with id ${id}: ${err}`);
                    }
                    resolve();
                });
            } finally {
                db.close();
            }
        });
    });
    return promise;
};

exports.DBError = 'DBError';
exports.UserExposedError = 'UserExposedError';
exports.NotFoundError = 'NotFoundError';
exports.initialSetup = initialSetup;
exports.createFeedback = createFeedback;
exports.readFeedbackById = readFeedbackById;
exports.readFeedbackByFoodId = readFeedbackByFoodId;
exports.updateFeedback = updateFeedback;
exports.deleteFeedback = deleteFeedback;
