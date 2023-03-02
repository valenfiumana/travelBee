import "./MyBookings.css"
import ProductHeader from '../ProdutcDetails/ProductHeader/ProductHeader'
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../../context/UserContext";
import getData from "../../utils/getData"
import jwt_decode from "jwt-decode";
import { Link } from "react-router-dom";
import axios from "axios";
let fetchArray = [];
import { ConsoleView } from "react-device-detect";
import ProductCardBooking from "./ProductCardBooking/ProductCardBooking";


const MyBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [products, setProducts] = useState([]);
  const [arrayPromisesDone, setArray] = useState([]);
  const userContext = useContext(UserContext);
  //jwt
  const jwt = localStorage.getItem('jwt');
  const decoded = jwt_decode(jwt);
  const userId = decoded.id;
  //endoints
  const baseURL = import.meta.env.VITE_BASE_URL;
  const myBookingsEndPoint = `${baseURL}/bookings/user/${userId}`;
  const productsEndPoint = `${baseURL}/products`;
  //checking if user is logged
  const isLogged = userContext.isLogged
  const navigateTo = useNavigate();
  useEffect(() => {
    !jwt && navigateTo('/');
  }, [isLogged]);

  //adding jwt in header (security)
  const settings = {
    headers: {
        'Authorization': `Bearer ${jwt}`
    }
  }



  
  useEffect(() => {
    /* - - fetch bookings - - */
    getData(myBookingsEndPoint, settings)
    .then(res => {
      setBookings(res?.data) //saving bookings from user logged in
      const productsId = [];
      res?.data.forEach(booking => {
        productsId.push(booking.productId)
      });
      
      
      fetchArray = [];
      productsId?.map(productId => {
        /* - - fetch products - - */
        let url = `${productsEndPoint}/${productId}`
        fetchArray.push(url) //guardo todos los url para fetch en un array
      });
      
      let nuevoArray = [];
      const fetch = (url) => axios.get(url); 
      const promiseArray = fetchArray.map(fetch); //hago todos los fetch y guardo respuestas en array
      let promiseExecution = async () => {
        await Promise.all(promiseArray)
        .then( res => {
          res.forEach(element => {
            nuevoArray.push(element.data)
          });
          setArray(nuevoArray)
        })
      };
      promiseExecution();
       //saving products from each booking
    } );
  }, []);

 
  return (
    <div className="MyBookings">
      <ProductHeader title='Mis reservas' backPath={'/'} />
      <section className="section-reservasUser">
        <div className="product-container-reserva">
        <h3 className="pagetitle">Reservas de {decoded.name} {decoded.lastname}</h3>
            <h4 className="primerTexto" style={{color:'black'}}> Actualmente estas son tus reservas </h4>
            {
              /*ESTO FUNCIONA
              bookings.length && arrayPromisesDone.length && 
                <ProductCardBooking key={1} booking={bookings[1]} product={arrayPromisesDone[1]}/>
              */
              //ESTO NO
                bookings.length && arrayPromisesDone.length && bookings.map((booking, i) => {
                  return <ProductCardBooking key={i} booking={bookings[i]} product={arrayPromisesDone[i]}/>
                  console.log(i)
                })    
            }
        </div> 
        {
            bookings.length == 0 && noBookingsMade()
          }
      </section>
    </div>
  );
};

//DARLE ESTILOS 
const noBookingsMade = () => {
  return (
    <>
  <div className="flex flex-row">
    <img className="imgError" src="https://travel-bee-images.s3.us-east-2.amazonaws.com/brand/emptyBookings.png" alt="errorIcon" />
    <p className="errorName">Error 404: No se encuentra ninguna reserva registrada</p>
  </div>
  </>
  )
}

/* exampleProducts.map((product, i) =>{
<ProductCardBooking key={index} 
                title={product.title} image={product.images[0]} direction={product.direction} city={product.city}
                checkin={booking.checkin} checkout={booking.checkout}/> */  


export default MyBookings

/*
<ProductCardBooking key={i} booking={bookings[i]} product={arrayPromisesDone[i]}/>
*/