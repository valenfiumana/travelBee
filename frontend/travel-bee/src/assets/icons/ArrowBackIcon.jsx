import Icon from '@ant-design/icons';

const ArrowBackSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 3.204 18.238 32" width="18.238" height="32"><path d="M15.999 35.195 0 19.197 15.999 3.2l2.239 2.28L4.521 19.197 18.238 32.916Z"/></svg>
  );

  const ArrowBackIcon = (props) => <Icon component={ArrowBackSvg} {...props} />;

export default ArrowBackIcon
