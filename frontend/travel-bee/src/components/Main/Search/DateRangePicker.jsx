import { DatePicker, Space } from 'antd';
const { RangePicker } = DatePicker;
import moment from 'moment';
import { useContext, useEffect, useRef } from 'react';
import { ProductContext } from '../../../context/ProductContext';
import dayjs from 'dayjs';

function DateRangePicker() {
  const searchInput = useRef(null);
  const { range } = useContext(ProductContext);
  const [rangeValue, setRangeValue] = range;

  const disabledDate = (current) => {
    return current && current < moment().startOf('day');
  };
  
  function handleChange(dates) {
    const from = dates ? moment(dates[0]).toDate() : undefined;
    const to = dates ? moment(dates[1]).toDate() : undefined;
    let newRange;
    if (from === undefined && to === undefined) {
      newRange = undefined;
    } else {
      newRange = { from: from , to: to}
    }
    setRangeValue(newRange);
    searchInput.current.blur();
  }

  return (
    <Space direction="vertical" size={12}>
      <RangePicker 
        ref={searchInput} 
        format='DD/MM/YYYY' 
        disabledDate={disabledDate} 
        onChange={handleChange} 
        placeholder={['Check in', 'Check out']}
        defaultValue={rangeValue === undefined ? '' : rangeValue.from && [dayjs(rangeValue.from), dayjs(rangeValue.to)]}
      />
    </Space>
  );
}
  
export default DateRangePicker
