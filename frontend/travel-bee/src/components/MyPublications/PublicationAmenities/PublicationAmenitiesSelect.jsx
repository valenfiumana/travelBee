import { useContext, useEffect, useState } from "react";
import { PublicationContext } from "../../../context/PublicationContext";
import getData from "../../../utils/getData";
import "./PublicationAmenitiesSelect.css";


function PublicationAmenitiesSelect({children, thisAmenities}){
  const [amenities, setAmenities] = useState([]);
  const { publicationDataValue, infoUpdatedValue, amenitiesUpdatesValue } = useContext(PublicationContext);
  const [infoUpdated , setInfoUpdated] = infoUpdatedValue;
  const [publicationData, setPublicationData] = publicationDataValue;
  const [amenitiesUpdates, setAmenitiesUpdates] = amenitiesUpdatesValue;
  const baseURL = import.meta.env.VITE_BASE_URL;
  const amenitiesEndPoint = `${baseURL}/features`;
  
  useEffect(() => {
    getData(amenitiesEndPoint)
    .then(res => setAmenities(res?.data));
  }, [amenitiesUpdates]);

  useEffect(() => {
    if (thisAmenities && infoUpdated) {
      const actualAmenities = thisAmenities.map(amenity => ({id: amenity.id}));
      setPublicationData({...publicationData, featuresIds: actualAmenities})
    }
  }, [thisAmenities, infoUpdated]);

  useEffect(() => {
    publicationData.featuresIds.length && !amenitiesUpdates && setAmenitiesUpdates(true);
  }, [publicationData]);

  function handleChange(e) {
    const isChecked = e.target.checked
    const amenityId = parseInt(e.target.value)
    isChecked && setPublicationData({...publicationData, featuresIds: [...publicationData.featuresIds, {id: amenityId}]});
    !isChecked && setPublicationData({...publicationData, featuresIds: [...(publicationData.featuresIds.filter(feature => feature.id !== amenityId))]});
  }

  function amenityExist(amenityId) {
    const thisAmenities = [...(publicationData.featuresIds)]
    const thisAmenity = thisAmenities.filter(amenity => amenity.id === amenityId);
    return thisAmenity[0] ? true : false;
  }

  return (
    <div className="attributes-select">
      <h4>Seleccionar amenities</h4>
      <div className="attributes-container">
        <div className="attributes-options"> 
          {amenities?.map(amenity => 
            <div key={amenity.id} className="amenity-option">
              <label onChange={handleChange}>
                <input type="checkbox" value={amenity.id} onChange={() => amenityExist(amenity.id) } checked={amenityExist(amenity.id)}/>
                <span className="checkmark"><div className="check"></div></span>
                <img src={amenity.icon_url} alt="" />
                {amenity.title}
              </label>
            </div>)}
        </div>
        {children}
      </div>
    </div>
  );
}

export default PublicationAmenitiesSelect
