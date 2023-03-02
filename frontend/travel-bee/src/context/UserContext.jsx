import { createContext, useEffect, useState } from "react";

const UserContext = createContext();

function UserContextProvider ({children}) {

    const [ isLogged, setIsLogged] = useState(false)
    const [ user, setUser ] = useState({})
    useEffect(() => {
        setUser({
            firstName: '',
            lastName: '',
            email: '',
            userId: '',
            role: '',
        })
    }, [])

    return (
        <UserContext.Provider value={{user: [user, setUser], isLogged, setIsLogged}} >
            {children}
        </UserContext.Provider>
    )
}

export { UserContext, UserContextProvider }
