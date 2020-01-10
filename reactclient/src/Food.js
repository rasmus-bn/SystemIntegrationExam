import React from 'react'
import { getAllCategories } from './data'

export default class Food extends React.Component {
    constructor() {
        super();
        this.state = { categories: [], foodsOfCategories:[], categoryPicked: "", categoryFinished: false, foodChoiceFinished: false }
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
            buttons.push(<button onClick={this.chooseFood}>{this.state.foodsOfCategories[i].name}</button>);
        }
        return buttons;
    }
    chooseFood = (e) => {
        alert(e.target.innerText);
        console.log(e.target.innerText);
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
            {this.getFoodsOnData(this.state.categoryPicked)}
            {this.presentCategorizedFoods()}
            </div>
        }
    }

    render() {
        return <div>
            {this.foodSteps()}
        </div>
    }
}