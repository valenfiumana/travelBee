import './Description.css'

function Description({description}) {  
  return (
    <div className="Description">
      <h2>Sobre este lugar</h2>
      <p>{description}</p>
    </div>
  );
}
  
export default Description
