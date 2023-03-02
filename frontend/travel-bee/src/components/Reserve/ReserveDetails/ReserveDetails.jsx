import Swal from 'sweetalert2'
import { useContext, useEffect, useState } from "react";
import LocationIcon from "../../../assets/icons/LocationIcon";
import { ProductContext } from "../../../context/ProductContext";
import "./ReserveDetails.css"
import { ReserveContext } from '../../../context/ReserveContext';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../../context/UserContext';
import postData from '../../../utils/postData';
import successMessage from '../../../utils/successMessage';
import BookingDates from './BookingDates/BookingDates';

function ReserveDetails ({ thisProduct }) {
    const { range, categoryActiveIndex, selectedCategory } = useContext(ProductContext);
    const [rangeValue, setRangeValue] = range;
    const [activeIndex, setActiveIndex] = categoryActiveIndex;
    const [selectedCategoryValue, setSelectedCategoryValue] = selectedCategory;
    const { originCity, arrivalTime } = useContext(ReserveContext);
    const [originCityValue, setOriginCityValue] = originCity;
    const [arrivalTimeValue, setArrivalTimeValue] = arrivalTime;
    const { user } = useContext(UserContext);
    const [userValue, setUserValue] = user;
    const navigateTo = useNavigate();
    const baseURL = import.meta.env.VITE_BASE_URL;
    const bookingEndPoint = `${baseURL}/bookings`;

    useEffect(() => {
        !localStorage.getItem('jwt') && navigateTo('/');
    }, [localStorage.getItem('jwt')])

    function handleClick() {
        let message = ''
        function infoMessage(message) {
            Swal.fire({
                text: message,
                icon: 'info',
                confirmButtonColor: 'var(--yellow)',
            })
        }

        if (!originCityValue) {
            message = 'Debes indicar una ciudad de origen para realizar la reserva.';
            infoMessage(message);
        } else if (!rangeValue) {
            message = 'Debes seleccionar un rango de fechas para realizar la reserva.';
            infoMessage(message);
        } else if (!rangeValue.from || !rangeValue.to){
            message = 'Debes seleccionar fecha de check-out para realizar la reserva.';
            infoMessage(message);
        } else if (!arrivalTimeValue) {
            message = 'Debes indicar un horario estimado de llegada para realizar la reserva.';
            infoMessage(message);
        }else {
            const bookingData = {
                checkin: `${rangeValue.from.toISOString().split('T')[0]}`,
                checkout: `${rangeValue.to.toISOString().split('T')[0]}`,
                arrivalTime: `${arrivalTimeValue}`,
                cityOfOrigin: `${originCityValue}`,
                productId: thisProduct.id,
                userId: userValue.userId
            }

            const settings = {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwt')}`
                }
            }

            postData(bookingEndPoint, bookingData, settings)
            .then(res => {
                if (res) {
                    window.scrollTo(0, 0);
                    successMessage('Reserva exitosa!', 2000)
                    setTimeout(() => {
                        setRangeValue();
                        setActiveIndex(1);
                        setSelectedCategoryValue(1);
                        navigateTo('/')
                    }, 2000);
                }
            });
        }
    }

    useEffect(() => {
        bookedNight();
    }, [rangeValue])

    function bookedNight() {
        if (rangeValue?.from && rangeValue?.to) {
            const timeDifference = rangeValue.to.getTime() - rangeValue.from.getTime();
            const dayDifference = timeDifference / (1000 * 3600 * 24);
            return dayDifference;
        }
    }
    
    return (
        <div className="ReserveDetails">
            <div className="reserve-header">
                <h2>Detalle de la reserva</h2>
                <img src={thisProduct.images[0].url} alt={thisProduct.images[0].title} />
            </div>
            <div className="reserve-details">
                <h3>{thisProduct.category.name.slice(0, -1)}</h3>
                <h2>{thisProduct.title}</h2>
                <div className="reserve-location">
                    <LocationIcon style={{fill: 'black', height: '16px'}}/>
                    <h3>{`${thisProduct.city.name}, ${thisProduct.city.country}`}</h3>
                </div>
                <div className="dates-range">
                    <BookingDates rangeValue={rangeValue} classNameMod={'in'} />
                    <BookingDates rangeValue={rangeValue} classNameMod={'out'} />
                </div>
                <div className='price'>
                    <div>
                        <p>Precio por noche: </p>
                        <p><strong> $ {thisProduct.price}</strong></p>
                    </div>
                    <div>
                        <p>Cantidad de noches: </p>
                        <p><strong>{bookedNight() ? bookedNight() : 0}</strong></p>
                    </div>
                    <div>
                        <p>Total: </p>
                        <p><strong>$ {bookedNight() ? bookedNight() * thisProduct.price : 0}</strong></p>
                    </div>
                </div>
            </div>
            <button onClick={handleClick}>Confirmar Reserva</button>
        </div>
    );
}
  
export default ReserveDetails
