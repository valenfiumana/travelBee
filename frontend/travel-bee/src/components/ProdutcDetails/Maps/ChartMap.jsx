import {MapContainer,Marker, TileLayer, Popup} from "react-leaflet";
import './ChartMap.css'

const ChartMap = ({longitude, latitude, city}) => {
  const position = [latitude, longitude];

  return (
    <div className="Map">
      <h2>Ubicación</h2>
      <div className="map-container" >
        <h3>{`${city.name}, ${city.country}`}</h3>
        <MapContainer
          center={position} 
          zoom={14} 
          zoomControl={true} 
          scrollWheelZoom={false}
          style={{ height: '250px', width: '100%' }}>
          <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          <Marker position={position}>
          <Popup>
          </Popup>
          </Marker>
        </MapContainer>
      </div>
    </div>
  )
}
  
export default ChartMap
