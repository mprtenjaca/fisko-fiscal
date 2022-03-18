import { combineReducers } from 'redux';
import auth from './authReducer.js';
import customersRed from './customerReducer';
import outputInvoiceRed from './outputInvoiceReducer';
import serviceRed from './serviceReducer';
import companyRed from './companyReducer';

export default combineReducers({
    auth,
    customersRed,
    outputInvoiceRed,
    serviceRed,
    companyRed,
});
