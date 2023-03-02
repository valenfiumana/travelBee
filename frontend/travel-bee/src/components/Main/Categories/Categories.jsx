import './Categories.css'
import CategoryIcon from './CategoryIcon/CategoryIcon';
import { useState, useContext, useEffect } from 'react';
import getData from '../../../utils/getData';
import { ProductContext } from '../../../context/ProductContext';

function Categories() {
  const [categories, setCategories] = useState([]);
  const { categoryActiveIndex } = useContext(ProductContext)
  const [activeIndex, setActiveIndex] = categoryActiveIndex
  const baseURL = import.meta.env.VITE_BASE_URL;
  const allCategoriesEndPoint = `${baseURL}/categories`

  useEffect(() => {
    getData(allCategoriesEndPoint)
    .then(res => setCategories(res?.data))
  }, [])

  function changeActiveIndex (e) {
    setActiveIndex(parseInt(e.target.id));
  }

  return (
    <div className="Categories">
      <div className='icons-container'>
        { !categories ? <p style={{margin: 'auto'}}>El servidor no responde, Categorías no encontradas.</p> : categories.map((category) => <CategoryIcon changeActiveIndex={changeActiveIndex} isActive={activeIndex === category.id} key={category.id} category={category} size='32px' />) }
      </div>
    </div>
  );
}
  
export default Categories
