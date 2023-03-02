import { useContext, useEffect, useState } from "react";
import { ReserveContext } from "../../../context/ReserveContext";
import { UserContext } from "../../../context/UserContext";
import "./UserForm.css"

function UserForm () {
  const { originCity } = useContext(ReserveContext);
  const [originCityValue, setOriginCityValue] = originCity;
  const { user } = useContext(UserContext);
  const [userValue, setUserValue] = user; 

  useEffect(() => {

  },[]);

  function handleChange(e) {
    setOriginCityValue(e.target.value);
  }
    
  return (
    <div className="UserForm">
      <div className="container">
        <div className="input">
          <label className="name">Nombre</label>
          <input name="name" type="text" className='disabled' value={userValue.firstName} disabled />
        </div>
        <div className="input">
          <label className="last-name">Apellido</label>
          <input name="last-name" type="text" className='disabled' value={userValue.lastName} disabled />
        </div>        
      </div>
      <div className="container">
        <div className="input">
          <label className="email">Correo electrónico</label>
          <input name="email" type="email" className='disabled' value={userValue.email} disabled />
        </div>
        <div className="input">
          <label className="city">Procedencia</label>
          <input onChange={handleChange} className="city" name="city" type="text" style={{ textTransform: 'capitalize' }} autoFocus required placeholder="Indique su ciudad de procedencia" />
        </div>    
      </div>
    </div>      
  );

}
  
export default UserForm
