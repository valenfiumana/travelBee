import ProductCard from './ProductCard/ProductCard';
import './ProductGrid.css'
import { useEffect, useContext, useState } from 'react';
import { ProductContext } from '../../../context/ProductContext';
import buildFetchURL from '../../../utils/builtFetchURL';
import getData from '../../../utils/getData';
import CardSkeleton from './ProductCard/CardSkeleton';

function ProductGrid() {
  const { products, selectedCategory } = useContext(ProductContext);
  const [ productsValue, setProductsValue ] = products;
  const [ selectedCategoryValue, setSelectedCategoryValue ] = selectedCategory;
  const [ isLoading, setIsLoading ] = useState(true);
  const baseURL = import.meta.env.VITE_BASE_URL;
  const producsEndPoint = `${baseURL}/products`;
  const fetchURL = buildFetchURL(producsEndPoint, selectedCategoryValue);

  useEffect(() => {
    setIsLoading(true)
    if(selectedCategoryValue !== 0) {
      getData(fetchURL)
      .then(res => {
        if (res?.status === 200) {
          setProductsValue(res?.data);
        }
      });
    }
  }, [selectedCategoryValue]);

  useEffect(() => {
    setTimeout(() => {
      productsValue ? setIsLoading(false) : setIsLoading(null);
    }, 2000);
  }, [productsValue]);
   
  return (
    <div className="ProductGrid">
        {isLoading ? <CardSkeleton cards={12} /> :
        productsValue?.map((product, i) => <ProductCard key={i} product={product} />)}
        {(isLoading === false && !productsValue?.length) && <p style={{margin: "80px"}}>No se encontraron residencias disponibles.</p> }
        {isLoading === null && <p>El servidor no responde, Residencias no encontradas.</p> }
    </div>
  );
}  

export default ProductGrid
