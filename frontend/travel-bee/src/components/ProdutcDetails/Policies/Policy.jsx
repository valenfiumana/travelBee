function Policy({policies}) {  
  return (
    <div className="Policy">
        <h3>{policies[0]?.type}</h3>
        <ul>
          {policies.map( (policy, i) => <li key={i}>{policy.description}</li>)}
        </ul>
    </div>
  );
}
  
export default Policy