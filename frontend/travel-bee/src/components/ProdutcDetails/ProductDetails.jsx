import './ProductDetails.css';
import LocationBar from './LocationBar/LocationBar';
import ProductHeader from './ProductHeader/ProductHeader';
import Calendar from './Calendar/Calendar';
import Amenities from './Amenities/Amenities';
import Description from './Description/Description';
import Gallery from './Gallery/Gallery';
import React, { useEffect, useState } from 'react';
import Policies from './Policies/Policies';
import ChartMap from './Maps/ChartMap.jsx';


function ProductDetails ({thisProduct}) {
    const [serverMessage, setServerMessage] = useState('');

    useEffect(()=> {
        setTimeout(() => {
            setServerMessage('El servidor no responde.')
        }, 2000)
    }, [])

    return (
        <div className='product-details'>
            {thisProduct &&
            <>
                <ProductHeader category={thisProduct.category} title={thisProduct.title} backPath={'/'} />
                <LocationBar city={thisProduct.city} />
                <Gallery imagesList={thisProduct.images}/>
                <Description description={thisProduct.description} />
                <Amenities amenities={thisProduct.features} />
                <Policies policies={thisProduct.policies} />
                <Calendar productId={thisProduct.id} bookings={thisProduct.bookings} />
                <ChartMap latitude={thisProduct.latitude} longitude={thisProduct.longitude} city={thisProduct.city} />
            </>}
            {!thisProduct && <p style={{margin: '200px'}}>{serverMessage}</p>}
        </div> 
    );
}

export default ProductDetails
