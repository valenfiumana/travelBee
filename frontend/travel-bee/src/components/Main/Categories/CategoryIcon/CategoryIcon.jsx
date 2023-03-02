import { useContext } from 'react';
import { ProductContext } from '../../../../context/ProductContext';
import './CategoryIcon.css'

function CategoryIcon({category, size, isActive, changeActiveIndex}) {
  const { products, selectedCategory } = useContext(ProductContext);
  const [ selectedCategoryValue, setSelectedCategoryValue ] = selectedCategory;
  
  function handleClick (e) {
    changeActiveIndex(e);
    setSelectedCategoryValue(parseInt(e.target.id));
  }

  return (
  <div onClick={handleClick} className={"CategoryIcon " + category.name.split(" ").join("") + " " + (isActive ? ' selected-category' : 'inactive-category')} id={category.id}>
      <button id={category.id} >
          <img src={category.imageUrl} width={size} height={size} id={category.id}></img>
          <p id={category.id}>{category.name}</p>
      </button>
  </div>
  );
}
  
export default CategoryIcon
