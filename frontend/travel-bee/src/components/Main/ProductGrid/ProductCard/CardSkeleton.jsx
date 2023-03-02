import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'
// import './ProductCard.css'

function CardSkeleton({cards}) {

  return Array(cards).fill(0).map((item, i) => (
        <div key={i} className="CardSkeleton">
            <div className='img' ><Skeleton height={'250px'} borderRadius={'20px'} /></div>
            <div className='h3'><Skeleton height={'26px'} /></div>
            <div className='p'><Skeleton height={'10px'} count='2'/></div>
            <div className='button'><Skeleton height={'22px'} margin={'0'} /></div>
        </div>
    ));
}
  
export default CardSkeleton 
  