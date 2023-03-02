import './ProductCard.css'
import { Link, useNavigate} from "react-router-dom";
import LinesEllipsis from 'react-lines-ellipsis';
import LocationIcon from '../../../../assets/icons/LocationIcon';
import { UserContext } from '../../../../context/UserContext';
import { useContext } from 'react';

function ProductCard({product}) {
  const navigateTo = useNavigate();
  const { user } = useContext(UserContext);
  const [userValue, setUserValue] = user;

  function handleClick() {
    navigateTo(`/residencia/${product.id}`);
  }
  
  return (
    <div className="ProductCard">
        <h4 className='category'>{(product.category.name).slice(0, -1)}</h4>
        <img onClick={handleClick} className='img' src={product.images[0].url} alt="" />
        <div className="product-info">
            <div className="category-location">
                <LocationIcon style={{fill: 'var(--middle-gray)', height:'12px'}} />
                <LinesEllipsis
                  className='h4'
                  text={`${product.city.name}, ${product.city.country}`}
                  maxLine='1'
                  ellipsis='...'
                  trimRight
                  basedOn='letters'>
                </LinesEllipsis>
            </div>
            <LinesEllipsis
              className='h3'
              text={product.title}
              maxLine='1'
              ellipsis='...'
              trimRight
              basedOn='letters'>
            </LinesEllipsis>
            <LinesEllipsis
              text={product.description}
              maxLine='2'
              ellipsis='...'
              trimRight
              basedOn='letters'>
            </LinesEllipsis>
            <div className='price'>
              <p><strong>${product.price}</strong> noche</p>
            </div>
            <Link to={ userValue?.role === 2 ? `/editar/${product.id}` : `/residencia/${product.id}`}><button style={{backgroundColor: `${ userValue?.role === 2 ? '#ff3d3d' : 'var(--yellow)'}`}} >{ userValue?.role === 2 ? 'Editar' : 'Ver más'}</button></Link>
        </div>
    </div>
  );
}
  
export default ProductCard
