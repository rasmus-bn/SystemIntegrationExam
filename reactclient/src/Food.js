import React from 'react'

export default class Food extends React.Component {
    constructor() {
        super();
        this.state = { categories: [], foodsOfCategories:[], foodName: "", categoryPicked: "", categoryFinished: false, foodChoiceFinished: false, foodDescription: "", foodId : null}
    }

    componentDidMount() {
        fetch("http://localhost:5009/api/v1/food/categories", {
            method: "get", headers: {
                'Content-Type': 'application/json'
            }
        }).then((data) => {
            return data.json();
        }).then((json) => {
            this.setState({ categories: json });
        })
    }

    foodCategoryButtons = () => {
        var buttons = [];
        for (var i = 0; i < this.state.categories.length; i++) {
            buttons.push(<button onClick={this.chooseCategory}>{this.state.categories[i]}</button>);
        }
        return buttons;
    }

    chooseCategory = (e) => {
        console.log(e.target.innerText);
        this.setState({ categoryPicked: e.target.innerText, categoryFinished: true });
        this.getFoodsOnData(e.target.innerText);
    }

    getFoodsOnData = (category) => {
        fetch("http://localhost:5009/api/v1/food/category?name="+category, {method: "get", headers:{
            'Content-Type': 'application/json'    
        }
    }).then((data)=>{
        return data.json();
    }).then((json) => {
        this.setState({foodsOfCategories: json});
    })
    }
    presentCategorizedFoods = () => {
        var buttons = [];
        for (var i = 0; i < this.state.foodsOfCategories.length; i++) {
            buttons.push(<button id={this.state.foodsOfCategories[i].id} onClick={this.chooseFood}>{this.state.foodsOfCategories[i].name}</button>);
        }
        return buttons;
    }
    chooseFood = (e) => {
        this.setState({foodName: e.target.innerText, foodId: e.target.id});
        fetch("http://localhost:5009/api/v1/food/" + e.target.id, {           method: "get", headers: {
            'Content-Type': 'application/json'
        }
    }).then((data) => {
        return data.json();
    }).then((json) => {
        this.setState({foodDescription : json.description});
    })
        console.log(e.target.innerText);
    }

    confirmFood = () => {
        if(this.state.foodDescription !== ""){
            return (<div>
        <h1>food description: {this.state.foodName}</h1>
                <p>{this.state.foodDescription}</p>
                <button onClick={this.foodPurchased}>confirm food choice</button>
            </div>)
        }
    }

    foodPurchased = () => {
    
        var data = {bookingId : this.props.bookingId, ticketId: this.props.ticketId, foodName: this.state.foodName, description: this.state.foodDescription}
        alert(data.name);
        fetch("http://localhost:5009/api/v1/food/saveBookings",{method: "post", headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data)})
        .then((res)=>{
            return res.text();
        }).then((text)=>{alert(text);})
        {this.props.foodDone("food done", this.state.foodId)}
    }

    foodSteps = () => {
        if (this.state.categoryFinished === false && this.state.foodChoiceFinished === false) {
            return <div>booking id:{this.props.bookingId}
                , ticket id:{this.props.ticketId}
                <h1>Food</h1>
                {this.foodCategoryButtons()}</div>
        }
        if (this.state.categoryFinished === true && this.state.foodChoiceFinished === false) {
        return <div><p>time for food pick {this.state.categoryPicked}</p>
            {this.presentCategorizedFoods()}
            {this.confirmFood()}
            </div>
        }
    }

    render() {
        return <div>
            {this.foodSteps()}
        </div>
    }
}