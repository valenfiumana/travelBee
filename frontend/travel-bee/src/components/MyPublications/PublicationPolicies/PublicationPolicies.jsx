import { useState } from "react";
import { useEffect } from "react";
import { useContext } from "react";
import { PublicationContext } from "../../../context/PublicationContext";
import compareIndex from "../../../utils/comapreIndex";

function PublicationPolicies({productPolicies}){
  const { publicationDataValue, amenitiesUpdatesValue, policiesUpdatedValue } = useContext(PublicationContext);
  const [publicationData, setPublicationData] = publicationDataValue;
  const [amenitiesUpdates, setAmenitiesUpdates] = amenitiesUpdatesValue;
  const [policiesUpdated , setPoliciesUpdated] = policiesUpdatedValue;
  let timeout;
  const policies = [
    {
      title: 'Normas generales',
      example: 'Ej: Prohibido fumar, Se admiten mascotas.',
    },
    {
      title: 'Salud y seguridad',
      example: 'Ej: Detector de monóxido de carbono, Cámaras de seguridad.',
    },
    {
      title: 'Política de cancelación',
      example: 'Ej: Esta reserva no es reembolsable con cancelaciones dentro de las 72hs antes del check-in.',
    },
  ];
  const [policyValue, setPolicyValue] = useState([
    {index: 0, type:'Normas generales', description:''},
    {index: 1, type:'Salud y seguridad', description:''},
    {index: 2, type:'Política de cancelación', description:''},
  ]);


  useEffect(() => {
    if (productPolicies && amenitiesUpdates) {
      const actualPolicies = productPolicies.map((policy, i) => ({index: i, type: policy.type, description: policy.description}));
      setPolicyValue(actualPolicies);
      setPublicationData({...publicationData, policies: [...actualPolicies]});
    }
  }, [productPolicies, amenitiesUpdates]);
  
  useEffect(() => {
    publicationData.policies.length && !policiesUpdated && setPoliciesUpdated(true);
  }, [publicationData]);

  function handleChange(e) {
    const type = e.target.dataset.type;
    const description = e.target.value;
    const index = e.target.dataset.index;

    const newPolicies = [...(policyValue.filter(policy => policy['type'] !== type )), {index: index, type: type, description: description}];
    newPolicies.sort(compareIndex);
    setPolicyValue([...newPolicies]);

    //debounce state change
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      setPublicationData({...publicationData, policies: newPolicies});
    }, 300);
  }

  return (
    <div className="publication-policies">
      <h4>Políticas de la residencia</h4>
      <div className="policies-container">
        {policies.map((policy, i) =>
          <div key={i} className="policy-container input">
            <h5>{policy.title}</h5>
            <label>Descripción</label>
            <div >
              <textarea onChange={handleChange} data-index={i} value={policyValue[i].description} data-type={policy.title} name="name" type="text" required placeholder={policy.example} />
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default PublicationPolicies
