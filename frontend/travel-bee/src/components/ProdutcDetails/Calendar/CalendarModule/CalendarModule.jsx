import 'react-day-picker/dist/style.css';
import './CalendarModule.css'
import React, { useContext } from 'react';
import { DayPicker } from 'react-day-picker';
import { ProductContext } from '../../../../context/ProductContext';
import es from 'date-fns/locale/es';
import { useState } from 'react';
import { useEffect } from 'react';
const bookedRanges = [];


export default function CalendarModule({numberOfMonths, bookings}) {
  const { range } = useContext(ProductContext);
  const [rangeValue, setRangeValue] = range;
  const defaultMonth = rangeValue ? rangeValue.from : undefined;
  const [disabledDays, setDisabledDays] = useState([{ before: new Date() }]);
  const [clickedDay, setClickedDay] = useState();

  //add bookedRanges from backend to disableDays
  useEffect(() => {
    bookings?.map(booking => {
      const checkin = new Date(booking.checkin.split('-'));
      const checkout = new Date(booking.checkout.split('-'));
      const bookedRange = {from: checkin, to: checkout};
      bookedRanges.push(bookedRange);
    });
    setDisabledDays(current => [...current, ...bookedRanges]);
  }, [])


  // add range restriction when selecting first day if range;
  useEffect(() => {
    if (!clickedDay) return

    if (bookedRanges.length !== 0 && rangeValue !== undefined) {

      const rangeStartDays = bookedRanges.map(bookedRange => {
        return bookedRange.from.getTime();
      });
      const selectedStartDay = rangeValue.from.getTime();
      addRestrictionLimits(rangeStartDays, selectedStartDay)
    }
  }, [clickedDay])

  // remove range restriction
  useEffect(() => {
    if (rangeValue === undefined && disabledDays[disabledDays.length -1].after !== undefined ){
      console.log('desactivo restriccion');
      setDisabledDays(disabledDays.filter(item => item.temp === undefined));
    }
  }, [rangeValue])


  function addRestrictionLimits( rangeStartDays, selectedStartDay){
    const pastBookings = rangeStartDays.filter(day => day < selectedStartDay );
    const futureBoookings = rangeStartDays.filter(day => day > selectedStartDay );
    const upperLimit = { after: new Date(Math.min(...futureBoookings)), temp: true};
    const lowerLimit = { before: new Date(Math.max(...pastBookings)), temp: true};
    setDisabledDays(current => [...current, lowerLimit, upperLimit]);
  }

  function handleClick() {
    setRangeValue();
  }
  
  return (
    <div className='CalendarModule'>
    <DayPicker
      numberOfMonths={numberOfMonths}
      min={2}
      mode="range"
      defaultMonth={defaultMonth}
      selected={rangeValue}
      onSelect={setRangeValue}
      disabled={disabledDays}
      locale={es}
      onDayClick={setClickedDay}
    />
    <h4 onClick={handleClick}>Borrar fechas</h4>
    </div>
  );
}