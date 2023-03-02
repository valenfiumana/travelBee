import "./Reserve.css"
import CalendarModule from "../ProdutcDetails/Calendar/CalendarModule/CalendarModule";
import Policies from "../ProdutcDetails/Policies/Policies";
import ProductHeader from "../ProdutcDetails/ProductHeader/ProductHeader";
import ArrivalTime from "./ArrivalTime/ArrivalTime";
import {isMobileOnly} from 'react-device-detect';
import ReserveDetails from "./ReserveDetails/ReserveDetails";
import UserForm from "./UserForm/UserForm";
import CalendarContainer from "./CalendarContainer/CalendarContainer";
import { ReserveContextProvider } from "../../context/ReserveContext";
import { useEffect, useState } from "react";
import ChartMap from "../ProdutcDetails/Maps/ChartMap";

function Reserve ({thisProduct}) {
    const [serverMessage, setServerMessage] = useState('');
    const numberOfMonths = isMobileOnly ? 1 : 2;

    useEffect(()=> {
        setTimeout(() => {
            setServerMessage('El servidor no responde.')
        }, 2000)
    }, []);

    return (
        <div className="Reserve">
            <ReserveContextProvider>
                {thisProduct &&
                <>
                    <ProductHeader category={thisProduct.category} title={thisProduct.title} backPath={`/residencia/${thisProduct.id}`} />
                    <div className="reserve-data">
                        <div className="reserve-forms">
                            <UserForm />
                            <CalendarContainer numberOfMonths={numberOfMonths} />
                            <ArrivalTime />
                            <Policies policies={thisProduct.policies} />
                            <ChartMap latitude={thisProduct.latitude} longitude={thisProduct.longitude} city={thisProduct.city} />
                        </div>
                        <ReserveDetails thisProduct={thisProduct} />
                    </div>
                </>}
                {!thisProduct && <p style={{margin: '200px'}}>{serverMessage}</p>}
            </ReserveContextProvider>
        </div>
    );
}
  
export default Reserve
