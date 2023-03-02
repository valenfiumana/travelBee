import './LocationBar.css'
import LocationIcon from '../../../assets/icons/LocationIcon';

function LocationBar({city}) {

  return (
    <div className="LocationBar">
      <div className='location-info'>
        <LocationIcon style={{fill: 'black', height: '16px'}}/>
        <h3>{`${city.name}, ${city.country}`}</h3>
      </div>
      <div className='score'>
      </div>  
    </div>
  );
}
  
export default LocationBar
