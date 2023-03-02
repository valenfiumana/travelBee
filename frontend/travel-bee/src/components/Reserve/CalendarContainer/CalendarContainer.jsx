import CalendarModule from "../../ProdutcDetails/Calendar/CalendarModule/CalendarModule";
import "./CalendarContainer.css"

function CalendarContainer ({numberOfMonths}) {

  return (
    <div className="CalendarContainer">
      <CalendarModule numberOfMonths={numberOfMonths} />
    </div>
  );
}
  
export default CalendarContainer
