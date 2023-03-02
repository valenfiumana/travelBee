import './Amenities.css'

function Amenities({amenities}) {
  
  return (
    <div className="Amenities">
        <h2>Amenities</h2>
        <ul className='amenitiesItem'>
          {amenities.map(amenity => <li key={amenity.id}><img src={amenity.icon_url} alt="" /><p>{amenity.title}</p></li> )}
        </ul>
    </div>
  );
}
  
export default Amenities
