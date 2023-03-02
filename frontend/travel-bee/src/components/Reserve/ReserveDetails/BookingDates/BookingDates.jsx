function BookingDates({ rangeValue, classNameMod }) {
    const fromTo = classNameMod === 'in' ? 'from' : 'to';
    return (
        <div className={`check-${classNameMod}`}>
            <h4>{`Check ${classNameMod}`}</h4>
            <h3>{rangeValue === undefined ? '-- / -- / --' : rangeValue[fromTo] === undefined ? '-- / -- / --' : rangeValue[fromTo].toLocaleDateString('es')}</h3>
        </div>
    )
}

export default BookingDates
