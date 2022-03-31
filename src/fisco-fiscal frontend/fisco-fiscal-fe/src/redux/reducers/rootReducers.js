import { combineReducers } from 'redux';
import auth from './authReducer.js';
import customersRed from './customerReducer';
import outputInvoiceRed from './outputInvoiceReducer';
import inputInvoiceRed from './inputInvoiceReducer';
import serviceRed from './serviceReducer';
import companyRed from './companyReducer';

export default combineReducers({
    auth,
    customersRed,
    outputInvoiceRed,
    inputInvoiceRed,
    serviceRed,
    companyRed,
});
