import { createContext, useState } from "react";

const ReserveContext = createContext();

function ReserveContextProvider ({children}) {
    const [ originCity, setOriginCity ] = useState();
    const [ arrivalTime, setArrivalTime ] = useState();


    return (
        <ReserveContext.Provider value={{
            originCity: [originCity, setOriginCity], arrivalTime: [arrivalTime, setArrivalTime] }} >
            {children}
        </ReserveContext.Provider>
    )
}

export { ReserveContext, ReserveContextProvider }
