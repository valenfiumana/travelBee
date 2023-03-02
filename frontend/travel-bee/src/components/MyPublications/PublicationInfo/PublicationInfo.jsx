import { useContext, useEffect, useState } from "react";
import { PublicationContext } from "../../../context/PublicationContext";
import compareCountry from "../../../utils/comapreCountry";
import getData from "../../../utils/getData";

function PublicationInfo({ thisProduct, product_id }){
  const [ categories, setCategories ] = useState();
  const [ cities, setCities ] = useState();
  const { publicationDataValue, infoUpdatedValue } = useContext(PublicationContext);
  const [publicationData, setPublicationData] = publicationDataValue;
  const [infoUpdated , setInfoUpdated] = infoUpdatedValue;
  const [inputValue, setInputValue] = useState({
    name: '',
    categoryId: '',
    direction: '',
    cityId: '',
    latitude: '',
    longitude: '',
    price: '',
    description: '',
  });

  const baseURL = import.meta.env.VITE_BASE_URL;
  const allCategoriesEndPoint = `${baseURL}/categories`
  const allCitiesEndPoint = `${baseURL}/cities`;

  useEffect(() => {
    getData(allCategoriesEndPoint)
    .then(res => {
      const categoryList = res?.data;
      categoryList?.shift();
      setCategories(categoryList);
    });
    
    getData(allCitiesEndPoint)
    .then(res => {
      const cities = res?.data
      cities.sort(compareCountry);
      setCities(cities);
    });
  }, []);

  useEffect(() => {
    // !product_id && clearForm();
  }, [product_id])
  
  useEffect(() => {
    if (thisProduct && categories && cities) {
      const prooductInputValue = {...inputValue};
      prooductInputValue.name = thisProduct.title;
      prooductInputValue.direction = thisProduct.address === null ? '' : thisProduct.address;
      prooductInputValue.categoryId = thisProduct.category.id;
      prooductInputValue.cityId = thisProduct.city.id;
      prooductInputValue.latitude = thisProduct.latitude;
      prooductInputValue.longitude = thisProduct.longitude;
      prooductInputValue.price = thisProduct.price;
      prooductInputValue.description = thisProduct.description;
  
      setInputValue({...prooductInputValue});
      setPublicationData({...publicationData ,...prooductInputValue});
    }
  }, [thisProduct]);
  
  useEffect(() => {
    publicationData.name && !infoUpdated && setInfoUpdated(true);
  }, [publicationData]);

  function handleChange(e) {
    const key = e.target.id;
    const value = e.target.value;

    setInputValue({
      ...inputValue,
      [key]: value
    })

    setPublicationData({
      ...publicationData,
      [key]: value
    })
  }

  function clearForm() {
    // setPublicationData({
    //   featuresIds: [],
    //   policies: [],
    //   images: [],
    //   featuresIds: [],
    // });
    setInputValue({
      name: '',
      categoryId: '',
      direction: '',
      cityId: '',
      latitude: '',
      longitude: '',
      price: '',
      description: '',
    });
  }
  
  return (
    <div className="publication-info">
      <div className="input-container">
        <div className="input">
          <label>Nombre de la propiedad</label>
          <input onChange={handleChange} id="name" name="name" value={inputValue.name} type="text" required placeholder="Ej: Castillo Mosele" />
        </div>
        <div className="input">
          <label htmlFor="categories">Categoría</label>
          <select onChange={handleChange} required name="categories" value={inputValue.categoryId} id="categoryId">
            <option value="">Seleccione una categoría</option>
            { !categories ? <option value='El servidor no responde, caterorías no encontradas.' /> : categories.map((category) => <option key={category.id} value={category.id} data-category={category.id} >{category.name}</option>) }
          </select>
        </div>
      </div>
      <div className="input-container">
        <div className="input">
            <label>Dirección</label>
            <input onChange={handleChange} id="direction" name="direction" type="text" value={inputValue.direction} required placeholder="Ej: José C. Paz 348, Provincia de Buenos Aires" />
        </div>
        <div className="input">
          <label htmlFor="cities">Ciudad</label>
          <select onChange={handleChange} required value={inputValue.cityId} name="cities" id="cityId">
            <option value="">Seleccione una ciudad</option>
            {!cities ? <option value='El servidor no responde, ciudades no encontradas.' /> : cities.map((city) => <option key={city.id} value={city.id} data-city={city.id} >{`${city.name}, ${city.country}`}</option>) }
          </select>
        </div>
      </div>
      <div className="input-container location-price">
        <div className="location">
          <div className="input">
            <label>Latitud</label>
            <input onChange={handleChange} value={inputValue.latitude} id="latitude" name="name" type="number" required placeholder="Ej: -34.25208050" />
          </div>
          <div className="input">
            <label>Longitud</label>
            <input onChange={handleChange} value={inputValue.longitude} id="longitude" name="name" type="number" required placeholder="Ej: -58.82986138" />
          </div>
        </div>
        <div className="input">
          <label>Precio por noche</label>
          <input onChange={handleChange} value={inputValue.price} id="price" name="name" type="number" required placeholder="Ej: 250" />
        </div>
      </div>
      <div className="input description">
        <label>Descripción</label>
        <textarea onChange={handleChange} value={inputValue.description} id="description" required name="description" type="text" placeholder="Deje una descripción de la propiedas a publicar" />
      </div>  
    </div>
  );
}

export default PublicationInfo
