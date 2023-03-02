import React, { useEffect, useState } from 'react';
import getData from '../../utils/getData';
import { useParams, Routes, Route} from "react-router-dom";
import ProductDetails from './ProductDetails';
import Reserve from '../Reserve/Reserve';
import Error404 from '../../assets/404';

function ProductRouting () {
    const [ thisProduct, setThisProduct ] = useState();
    const {product_id} = useParams();
    const baseURL = import.meta.env.VITE_BASE_URL;
    const productEndPoint = `${baseURL}/products/${product_id}`;

    useEffect(() => {
        getData(productEndPoint)
        .then(res => setThisProduct(res?.data))
    },[])

    return (
        <Routes >
            <Route path={'/'} element={<ProductDetails thisProduct={thisProduct} />} />
            <Route path={'/reserva'} element={<Reserve thisProduct={thisProduct} />} />
            <Route path="*" element={<Error404 />} />
        </Routes>
    );
}
export default ProductRouting