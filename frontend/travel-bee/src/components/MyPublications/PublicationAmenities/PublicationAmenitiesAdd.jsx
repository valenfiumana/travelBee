import { PlusCircleFilled, CloseCircleFilled } from '@ant-design/icons';
import { useContext } from 'react';
import { useState } from 'react';
import { PublicationContext } from '../../../context/PublicationContext';

function PublicationAmenitiesAdd(){
  let timeout;
  const { newAmenitiesValue, amenitiesUpdatesValue } = useContext(PublicationContext);
  const [newAmenities, setNewAmenities] = newAmenitiesValue;
  const [amenitiesUpdates, setAmenitiesUpdates] = amenitiesUpdatesValue;
  const [inputList, setInputList] = useState([
    {input: 1},
  ]);

  function handleChange(e) {

    clearTimeout(timeout);
    timeout = setTimeout(() => {
      const key = e.target.className;
      const value = e.target.value;
      const id = e.target.dataset.number;
      const amenities = [...newAmenities];
      amenities[id][key] = value
      setNewAmenities(amenities);
    }, 300);
  }

  function addInput() {
    setNewAmenities([...newAmenities, {title: '', icon_url: ''}])
    setInputList([...inputList, {input: inputList.length + 1}]);
  }

  function removeInput(i) {
    const amenities = [...newAmenities]
    amenities.pop();
    setNewAmenities(amenities);

    const list = [...inputList];
    list.pop();
    setInputList(list);
  }

  return (
    <div className="publication-attributes">
      <h4>Crear amenities (Opcional)</h4>
      <div style={{backgroundColor: 'rgba(84, 87, 118, 0.1)', borderRadius: '10px', padding: '10px 0'}}>
        {inputList.map((input, i) =>
          <div key={i} className="input-container attributes" style={{margin: '0', backgroundColor: 'unset'}}>
            <div className="input attribute-input">
              <label>Nombre</label>
              <div className="attribute-upload attribute-name">
                <input form='AddAmenitiesForm' onChange={handleChange} data-number={i} className='title' name="name" type="text" required placeholder="Ej: WiFi" disabled={amenitiesUpdates >= 3} />
              </div>
            </div>
            <div className="input attribute-input">
              <label>Ícono</label>
              <div className="attribute-upload attribute-icon">
                <input form='AddAmenitiesForm' onChange={handleChange} data-number={i} className='icon_url' required name="name" type="text" accept=".png, .jpg, .jpeg, .svg" placeholder="Ej: https://flaticon.com/512/157/157839.png, .svg, .jpg, .jpeg" disabled={amenitiesUpdates >= 3} />
                {i === 0 && i < 2 && inputList.length < 3 - amenitiesUpdates && <PlusCircleFilled onClick={addInput} className='add-btn' style={{color: 'var(--yellow)', fontSize: '43px', marginLeft:'15px'}} />}
                {inputList.length > 1 && i === inputList.length -1 && <CloseCircleFilled onClick={() => removeInput(i)} className='remove-btn' style={{color: 'red', fontSize: '43px', marginLeft:'15px'}} />}
                {(inputList.length === 3 - amenitiesUpdates && i < inputList.length -1) && <div style={{height: '43px', minWidth: '43px', margin:'0 3px 0 15px'}}></div>}
              </div>
            </div>
          </div>
        )}
        <button type='submit' form='AddAmenitiesForm' id='add-amenites-btn'>{inputList.length === 1 ? 'Agregar amenity' : 'Agregar amenities'}</button>
      </div>
    </div>
  );
}

export default PublicationAmenitiesAdd
