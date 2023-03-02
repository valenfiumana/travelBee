import DateRangePicker from './DateRangePicker';
import Swal from 'sweetalert2';
import 'antd/dist/antd.css';
import './Search.css';
import LocationIcon from '../../../assets/icons/LocationIcon';
import {SearchOutlined} from '@ant-design/icons';
import { useEffect, useRef } from 'react';
import getData from '../../../utils/getData';
import { useState } from 'react';
import { useContext } from 'react';
import { ProductContext } from '../../../context/ProductContext';
import compareCountry from '../../../utils/comapreCountry';

function Search() {
  const [ cities, setCities ] = useState([]);
  const [ selectedCityId, setSelectedCityId ] = useState();
  const { products } = useContext(ProductContext);
  const [ productsValue, setProductsValue ] = products;
  const { categoryActiveIndex, selectedCategory } = useContext(ProductContext);
  const [ CatactiveIndex, setCatActiveIndex] = categoryActiveIndex;
  const [ selectedCategoryValue, setSelectedCategoryValue ] = selectedCategory;
  const { range } = useContext(ProductContext);
  const [rangeValue, setRangeValue] = range;
  const baseURL = import.meta.env.VITE_BASE_URL;
  const allCitiesEndPoint = `${baseURL}/cities`;
  const checkInString = rangeValue ? `/${rangeValue.from?.toISOString().split('T')[0]}` : '';
  const checkOutString = rangeValue ? `/${rangeValue.to?.toISOString().split('T')[0]}` : '';
  const selectedCityIdString = `/${selectedCityId ? selectedCityId : ''}`;
  const filerEndPoint = `${baseURL}/products/filter${checkInString}${checkOutString}${selectedCityIdString}`;
  const inputRef = useRef(); 

  useEffect(() => {
    getData(allCitiesEndPoint)
    .then(res => {
      const cities = res?.data
      cities.sort(compareCountry);
      setCities(cities);
    });
  }, [])

  function handleChange(e) {

    const searchedCity = cities.find(city => `${city.name}, ${city.country}` === e.target.value );
    if (e.target.value === '') {
      setSelectedCityId();
    } else if (!searchedCity) {
      setSelectedCityId(0);
    } else {
      setSelectedCityId(searchedCity.id);
    }
  }

  function handleClick(e) {
    e.preventDefault();
    
    if (selectedCityId === undefined && !rangeValue) return
    if (selectedCityId === 0) {
      Swal.fire({
        text: 'Ciudad inválida, seleccione una ciudad del listado.',
        icon: 'info',
        confirmButtonColor: 'var(--yellow)',
      }).then((result) => {
        if (result.isConfirmed) {
          inputRef.current.value = '';
          setSelectedCityId();
        }
      })
    } else  {
      getData(filerEndPoint)
      .then(res => setProductsValue(res?.data));
      setCatActiveIndex();
      setSelectedCategoryValue(0);
    }
  }

  return (
    <div className="Search">
      <div className='searchInputs'>
        <h1>Encuentra tu próxima estadía</h1>
        <form>
          <div className='destino'>
            <LocationIcon style={{fill: 'lightgray', height: '19.6px'}}/>
            <input ref={inputRef} onChange={handleChange} className='cities' list='cities' placeholder='Elige tu destino' required/>
            <datalist id='cities' required>
              {!cities ? <option value='El servidor no responde, ciudades no encontradas.' /> : cities.map((city) => <option key={city.id} value={`${city.name}, ${city.country}`} data-city={city.id} />) }
            </datalist>
          </div>
          <div className="vl"></div>
          <div className='chInChOut'>
            <DateRangePicker />
            <button onClick={handleClick} ><p>Buscar</p><SearchOutlined style={{ fontSize: '24px', color: '#FFF' }} /></button>
            <i></i>
          </div>
        </form>
      </div>
    </div>
  );
}
  
export default Search
