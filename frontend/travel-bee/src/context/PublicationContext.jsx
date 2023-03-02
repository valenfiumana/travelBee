import { createContext, useState } from "react";

const PublicationContext = createContext();

function PublicationContextProvider ({children}) {
    const [ publicationData, setPublicationData ] = useState({
        featuresIds: [],
        policies: [],
        images: [],
    });
    const [newAmenities, setNewAmenities] = useState([{title: '', icon_url: ''}]);
    const [amenitiesUpdates, setAmenitiesUpdates] = useState(0);
    const [infoUpdated , setInfoUpdated] = useState(false);
    const [amenitiesUpdated , setAmenitiesUpdated] = useState(false);
    const [policiesUpdated , setPoliciesUpdated] = useState(false);
    const [imagesUpdated , setImagesUpdated] = useState(false);

    return (
        <PublicationContext.Provider value={{
            publicationDataValue: [publicationData, setPublicationData],
            newAmenitiesValue: [newAmenities, setNewAmenities],
            amenitiesUpdatesValue: [amenitiesUpdates, setAmenitiesUpdates],
            infoUpdatedValue: [infoUpdated , setInfoUpdated],
            amenitiesUpdatedValue: [amenitiesUpdated , setAmenitiesUpdated],
            policiesUpdatedValue: [policiesUpdated , setPoliciesUpdated],
            imagesUpdatedValue: [imagesUpdated , setImagesUpdated],
            }} >
            {children}
        </PublicationContext.Provider>
    )
}

export { PublicationContext, PublicationContextProvider }
