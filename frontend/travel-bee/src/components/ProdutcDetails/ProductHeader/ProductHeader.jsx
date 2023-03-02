import { Link } from 'react-router-dom';
import ArrowBackIcon from '../../../assets/icons/ArrowBackIcon';
import './ProductHeader.css';

function ProductHeader({category, title, backPath}) {
  return (
    <div className="ProductHeader">
      <div className='category-title'>
        <h4>{(category?.name)?.slice(0, -1)}</h4>
        <h2>{title}</h2>
      </div>
      <Link to={backPath}><ArrowBackIcon /></Link>
    </div>
  );
}
  
export default ProductHeader
