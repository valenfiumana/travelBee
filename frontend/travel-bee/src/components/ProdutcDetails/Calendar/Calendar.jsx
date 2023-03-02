import './Calendar.css'
import React, { useContext } from "react";
import {isMobileOnly} from 'react-device-detect';
import { UserContext } from '../../../context/UserContext';
import CalendarModule from './CalendarModule/CalendarModule';
import { Link, useLocation  } from "react-router-dom";

function Calendar({productId, bookings}) {
  const userContext = useContext(UserContext);
  const isLogged = userContext.isLogged;
  const location = useLocation(); 
  const numberOfMonths = isMobileOnly ? 1 : 2;
 
  return (
    <div className="Calendar">
      <h2>Disponibilidad</h2>
      <div className='range-confirmation'>
        <CalendarModule numberOfMonths={numberOfMonths} bookings={bookings} />
        {isLogged ? (
        <Link to={`${location.pathname + '/reserva'}`}>
          <button className='btnReserva' type='button'>Iniciar reserva</button>
        </Link>) : (
        <Link to={"/login"} state={{bookingMessage: "Ingresá para realizar una Reserva. ¡Gracias!", productId: productId }}>
            <button className='btnReserva' type='button'>Iniciar reserva</button>
        </Link>)}
      </div>  
    </div>
  );
}

export default Calendar

