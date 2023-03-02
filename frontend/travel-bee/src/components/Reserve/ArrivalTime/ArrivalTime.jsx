import "./ArrivalTime.css"
import { useContext, useEffect, useState } from "react";
import {CheckCircleOutlined} from '@ant-design/icons';
import { ReserveContext } from "../../../context/ReserveContext";

function ArrivalTime () {
  const [hours, setHours] =useState([]);
  const { arrivalTime } = useContext(ReserveContext);
  const [arrivalTimeValue, setArrivalTimeValue] = arrivalTime;

  useEffect(()=> {
    const arr = []
    for (let hour = 10; hour < 23; hour++) {
      arr.push(hour < 10 ? `0${hour}:00` : `${hour}:00`)
    }
    setHours(arr);
  },[])

  function handleChange(e) {
    setArrivalTimeValue(e.target.value);
  }
    
  return (
    <div className="ArrivalTime">
        <div className="title">
          <CheckCircleOutlined style={{fontSize: '32px', color: 'var(--gray)'}} />
          <h3>Puedes realizar el check-in entre las 10:00 y las 22:00 hs</h3>
        </div>
        <div className="hour-selection">
          <label htmlFor="hours">Indique su horario estimado de llegada:</label>
          <select onChange={handleChange} name="hours" id="hours">
            <option value="">-- : --</option>
            {hours.map((hour, i) => <option key={i} value={`${hour}:00`}>{hour}</option> )}
          </select>
        </div>
    </div>
  );
}
  
export default ArrivalTime
