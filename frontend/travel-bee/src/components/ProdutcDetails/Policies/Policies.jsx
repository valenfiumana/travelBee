import './Policies.css'
import Policy from './Policy';

function Policies({policies}) { 
  
  const generalRules = policies?.filter(policy => policy.type === 'Normas generales');
  const safety = policies?.filter(policy => policy.type === 'Salud y seguridad');
  const cancelation = policies?.filter(policy => policy.type === 'Política de cancelación');
  
  return (
    <div className="Policies">
      <h2>Qué tenes que saber</h2>
      <div className='policies-cointainer'>
        <Policy policies={generalRules}  />
        <Policy policies={safety}  />
        <Policy policies={cancelation}  />
      </div>
    </div>
  );
}
  
export default Policies
