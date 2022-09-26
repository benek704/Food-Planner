
const deleteElements = document.querySelectorAll("#delete");

deleteElements.forEach(del=>{
    del.addEventListener('click', event=>{
        if(!confirm('Czy na pewno chcesz usunąć?')){
            event.preventDefault();
        }
    })
})