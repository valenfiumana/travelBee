import "./ProductCardBooking.css"
import moment from "moment";

const ProductCardBooking = ({booking, product}) => {
    return(
        <div>
            <div className="booking-container">
                <div className="description">
                    <h3>{product.title}</h3>
                    <p className="categoryP">Reserva realizada en TravelBee</p>
                    <hr/>
                    <div className="datePlace">
                        <div className="date">
                            <p className="from">{moment(booking.checkin).format('DD')} {moment(booking.checkin).format('MMM')}</p>
                            <p className="to">{moment(booking.checkout).format('DD')} {moment(booking.checkout).format('MMM')}</p>
                            <p className="year">{moment(booking.checkin).format('yyyy')}</p>
                        </div>
                        <div className="vl"></div>
                        <div className="location">
                            <p className="city">{product.city.name}</p>
                            <p className="address">{product.address}</p>
                        </div>
                    </div>
                </div>
                <div className="img">
                    <img src={product.images[0].url} alt="" />
                </div>
            </div>
        </div>
    )
}

export default ProductCardBooking