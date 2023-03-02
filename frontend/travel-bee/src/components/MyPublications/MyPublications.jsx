import { useContext, useEffect, useReducer, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { UserContext } from "../../context/UserContext";
import ProductHeader from "../ProdutcDetails/ProductHeader/ProductHeader";
import "./MyPublications.css";
import PublicationInfo from "./PublicationInfo/PublicationInfo";
import PublicationAmenitiesAdd from "./PublicationAmenities/PublicationAmenitiesAdd";
import PublicationPolicies from "./PublicationPolicies/PublicationPolicies";
import PublicationImages from "./PublicationImages/PublicationImages";
import PublicationAmenitiesSelect from "./PublicationAmenities/PublicationAmenitiesSelect";
import { PublicationContext } from "../../context/PublicationContext";
import postData from '../../utils/postData';
import successMessage from '../../utils/successMessage'
import getData from "../../utils/getData";
import putData from "../../utils/putData";

const MyPublications = () => {
    const {product_id} = useParams();
    const [ thisProduct, setThisProduct ] = useState();
    const userContext = useContext(UserContext);
    const isLogged = userContext.isLogged;
    const { publicationDataValue, newAmenitiesValue, amenitiesUpdatesValue } = useContext(PublicationContext);
    const [publicationData, setPublicationData] = publicationDataValue;
    const [newAmenities, setNewAmenities] = newAmenitiesValue;
    const [amenitiesUpdates, setAmenitiesUpdates] = amenitiesUpdatesValue;
    const [policiesIds, setPoliciesIds] = useState([]);

    const navigateTo = useNavigate();
    const baseURL = import.meta.env.VITE_BASE_URL;
    const featuresEndPoint = `${baseURL}/features`;
    const productsEndPoint = `${baseURL}/products`;
    const productEditEndPoint = `${baseURL}/products/${product_id}`;
    const policiesEndPoint = `${baseURL}/policies`;

    const settings = {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
        }
    }

    useEffect(() => {
        !localStorage.getItem('jwt') && navigateTo('/');
    }, [isLogged]);

    useEffect(() => {
        product_id && getData(`${productsEndPoint}/${product_id}`).then(res => setThisProduct(res?.data));
    }, [])

    useEffect(() => {
        policiesIds.length && postProductData();
    }, [policiesIds]);

    function submit(e) {
        e.preventDefault();

        publicationData.policies.map(policy => {
            const policyData = policy;
            postData(policiesEndPoint, policyData, settings)
            .then(res => {
                setPoliciesIds(current => [...current, {id: res?.data.id}]);
            });
        });

    }

    function submitAddAmenities(e) {
        e.preventDefault();
        const form = e.target;
        const errMessage = 'Lamentablemente el/los amenities no han podido crearse. Por favor intente más tarde';
        newAmenities.map(amenity => {
            postData(featuresEndPoint, amenity, settings, errMessage)
            .then(res => {
                if (res) {
                    successMessage('Amenity creado exitosamente', 2000, 'top-end', false);
                    setAmenitiesUpdates(current => current + 1);
                }
            });
        });
        form.reset();
    }

    function postProductData() {
        const productDataNew = {
            "title": (publicationData?.name).charAt(0).toUpperCase() + (publicationData?.name).slice(1),
            "description": publicationData?.description,
            "address": publicationData?.direction,
            "price": parseInt(publicationData?.price),
            "longitude": parseFloat(publicationData?.longitude),
            "latitude": parseFloat(publicationData?.latitude),
            "category": {
                "id": parseInt(publicationData?.categoryId),
            },
            "city": {
                "id": parseInt(publicationData?.cityId),
            },
            "features": publicationData?.featuresIds,
            "policies": policiesIds,
            "images": publicationData?.images,
        }

        const productDataUpdate = {
            ...productDataNew,
            id: thisProduct?.id,
            bookings: thisProduct?.bookings
        };

        
        if (product_id) {
            const errMessage = 'Lamentablemente el producto no ha podido actualizarse. Por favor intente más tarde.'
            putData(productEditEndPoint, productDataUpdate, settings, errMessage)
            .then(res => {
                if (res) {
                  window.scrollTo(0, 0);
                  successMessage('Actualización exitosa!', 2000);
                  setTimeout(() => {
                    navigateTo('/');
                  }, 2000);
                }
            });
        } else {
            const errMessage = 'Lamentablemente el producto no ha podido crearse. Por favor intente más tarde.'
            postData(productsEndPoint, productDataNew, settings, errMessage)
            .then(res => {
                if (res) {
                  window.scrollTo(0, 0);
                  successMessage('Creación exitosa!', 2000);
                  setTimeout(() => {
                    navigateTo('/');
                  }, 2000);
                }
            });
        }

    }

    return (
        <div className="MyPublications">
            <ProductHeader title='Administrar publicaciones' backPath={'/'} />
            <div className="form-container">
                <h3>{product_id ? 'Editar publicación' : 'Crear publicación'}</h3>
                <form onSubmit={submit} className="publication-form">
                    <PublicationInfo thisProduct={thisProduct} product_id={product_id} />
                    <PublicationAmenitiesSelect thisAmenities={thisProduct?.features} >
                        <PublicationAmenitiesAdd />
                    </PublicationAmenitiesSelect>
                    <PublicationPolicies productPolicies={thisProduct?.policies} />
                    <PublicationImages productImages={thisProduct?.images} />
                    <button type="submit">{product_id ? 'Actualizar publicación' : 'Crear publicación'}</button>
                </form>
                <form onSubmit={submitAddAmenities} id="AddAmenitiesForm"></form>
            </div>
        </div>
    );
};
  
export default MyPublications
