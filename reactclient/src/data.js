export const getAllCategories = () => {
    fetch("http://localhost:5009/api/v1/food/categories", {method: "get", headers: {
        'Content-Type': 'application/json'
      }}).then((data)=>{
          return data.json();
      }).then((json)=>{
          return json;
      })
}