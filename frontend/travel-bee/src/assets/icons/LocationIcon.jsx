import Icon from '@ant-design/icons';

const LocationSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="4.32 2.33 15.35 19.6" width="100%" height="100%"><path d="M12 11.675q0.7 0 1.188 -0.487 0.487 -0.488 0.487 -1.188t-0.487 -1.188Q12.7 8.325 12 8.325t-1.188 0.487q-0.487 0.488 -0.487 1.188t0.487 1.188q0.488 0.487 1.188 0.487Zm0 10.25q-3.9 -3.5 -5.787 -6.462Q4.325 12.5 4.325 10.2q0 -3.6 2.325 -5.738Q8.975 2.325 12 2.325q3.025 0 5.35 2.137Q19.675 6.6 19.675 10.2q0 2.3 -1.887 5.263Q15.9 18.425 12 21.925Z"/></svg>
);

const LocationIcon = (props) => <Icon component={LocationSvg} {...props} />;

export default LocationIcon
