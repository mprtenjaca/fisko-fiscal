import React, { useContext, useEffect, useState } from "react";

// reactstrap components
import {
  Card,
  CardHeader,
  CardBody,
  CardTitle,
  Table,
  FormGroup,
  Form,
  Input,
  Row,
  Col,
} from "reactstrap";
import Button from "react-bootstrap/Button";

// core components
import "assets/css/fisco-custom.css";
import PanelHeader from "components/PanelHeader/PanelHeader.js";

import { customersHead } from "variables/general";
import { customerValidation } from "components/Validation/ValidateValues.js";
import { useDispatch, useSelector } from "react-redux";
import { updateCustomer } from "redux/actions/customerAction";
import { createCustomer } from "redux/actions/customerAction";
import { deleteCustomer } from "redux/actions/customerAction";

const Customers = () => {
  const initialCustomerDataState = {
    user: null,
    firstName: "",
    lastName: "",
    address: "",
    email: "",
    oib: "",
    city: "",
    postalCode: "",
    country: "",
    phoneNumber: "",
    fax: "",
  };

  const [isSubmit, setIsSubmit] = useState(false);
  const [isEditedCustomer, setIsEditedCustomer] = useState(false);
  const [customers, setCustomers] = useState([]);
  const [customerData, setCustomerData] = useState(initialCustomerDataState);
  const [formErrors, setFromErrors] = useState(initialCustomerDataState);
  const dispatch = useDispatch();
  const { customersRed, auth } = useSelector((state) => state);

  const {
    firstName,
    lastName,
    address,
    email,
    oib,
    city,
    postalCode,
    country,
    phoneNumber,
    fax,
  } = customerData;

  useEffect(() => {
    setCustomerData({ ...customerData, user: auth.user });
    setCustomers(customersRed.customers);
  }, [customersRed.customers, customersRed.loading]);

  const handleChangeInput = (e) => {
    const { name, value } = e.target;
    setCustomerData({ ...customerData, [name]: value });
    console.log(customerData);
  };

  const handleCustomerEdit = (data) => (e) => {
    setCustomerData(data);
    setIsEditedCustomer(true);
  };

  const handleDeleteCustomer = (e) => {
    console.log(customerData);
    dispatch(deleteCustomer(customerData));
    setIsEditedCustomer(false);
    setCustomerData(initialCustomerDataState);
  };

  const handleNewCustomerAction = (e) => {
    setIsEditedCustomer(!isEditedCustomer);
    setCustomerData(initialCustomerDataState);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFromErrors(customerValidation(customerData));
    setIsSubmit(true);

    if (Object.keys(customerValidation(customerData)).length === 0) {
      if (isEditedCustomer) {
        dispatch(updateCustomer(customerData));
        setCustomerData(initialCustomerDataState);
        setIsEditedCustomer(false);
      } else {
        dispatch(createCustomer(customerData));
        setCustomerData(initialCustomerDataState);
      }
    }
  };

  return (
    <>
      {console.log(customers)}
      <PanelHeader size="sm" />
      <div className="content">
        <Row>
          <Col md="12">
            <Card>
              <CardHeader>
                <Row>
                  <Col md="12" className="center-custom">
                    <h5 className="title">Add Customer</h5>
                    {isEditedCustomer ? (
                      <Button
                        variant="info"
                        type="button"
                        onClick={handleNewCustomerAction}
                      >
                        Add new
                      </Button>
                    ) : (
                      <></>
                    )}
                  </Col>
                </Row>
              </CardHeader>
              <CardBody>
                <Form onSubmit={handleSubmit}>
                  <Row>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>First Name</label>
                        <Input
                          placeholder="First name"
                          type="text"
                          value={firstName}
                          onChange={handleChangeInput}
                          name="firstName"
                        />
                        <p className="error">{formErrors.firstName}</p>
                      </FormGroup>
                    </Col>
                    <Col className="px-1" md="3">
                      <FormGroup>
                        <label>Last Name</label>
                        <Input
                          placeholder="Last name"
                          type="text"
                          onChange={handleChangeInput}
                          value={lastName}
                          name="lastName"
                        />
                        <p className="error">{formErrors.lastName}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Email</label>
                        <Input
                          placeholder="Email"
                          type="email"
                          onChange={handleChangeInput}
                          value={email}
                          name="email"
                        />
                        <p className="error">{formErrors.email}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pl-1" md="3">
                      <FormGroup>
                        <label>OIB</label>
                        <Input
                          placeholder="OIB"
                          type="number"
                          onChange={handleChangeInput}
                          value={oib}
                          name="oib"
                        />
                        <p className="error">{formErrors.oib}</p>
                      </FormGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col className="pl-1" md="3">
                      <FormGroup>
                        <label htmlFor="exampleInputEmail1">Address</label>
                        <Input
                          placeholder="Address"
                          type="text"
                          onChange={handleChangeInput}
                          value={address}
                          name="address"
                        />
                        <p className="error">{formErrors.address}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pl-1" md="3">
                      <FormGroup>
                        <label>City</label>
                        <Input
                          placeholder="City"
                          type="text"
                          onChange={handleChangeInput}
                          value={city}
                          name="city"
                        />
                        <p className="error">{formErrors.city}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Postal Code</label>
                        <Input
                          placeholder="Postal code"
                          type="number"
                          onChange={handleChangeInput}
                          value={postalCode}
                          name="postalCode"
                        />
                        <p className="error">{formErrors.postalCode}</p>
                      </FormGroup>
                    </Col>
                    <Col className="px-1" md="3">
                      <FormGroup>
                        <label>Country</label>
                        <Input
                          placeholder="Country"
                          type="text"
                          onChange={handleChangeInput}
                          value={country}
                          name="country"
                        />
                        <p className="error">{formErrors.country}</p>
                      </FormGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col className="pl-1" md="4">
                      <FormGroup>
                        <label>Phone Number</label>
                        <Input
                          placeholder="Phone number"
                          type="number"
                          onChange={handleChangeInput}
                          value={phoneNumber}
                          name="phoneNumber"
                        />
                        <p className="error">{formErrors.phoneNumber}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pl-1" md="4">
                      <FormGroup>
                        <label>Fax</label>
                        <Input
                          placeholder="Fax"
                          type="number"
                          onChange={handleChangeInput}
                          value={fax}
                          name="fax"
                        />
                      </FormGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col md="3" className="editor">
                      {isEditedCustomer ? (
                        <>
                          <Button variant="primary" type="submit">
                            Update customer
                          </Button>
                          <Button
                            variant="danger"
                            type="button"
                            onClick={handleDeleteCustomer}
                          >
                            Delete
                          </Button>
                        </>
                      ) : (
                        <Button variant="info" type="submit">
                          Add new customer
                        </Button>
                      )}
                    </Col>
                  </Row>
                </Form>
              </CardBody>
            </Card>
          </Col>
          <Col xs={12}>
            <Card className="card-plain">
              <CardHeader>
                <CardTitle tag="h4">Customers</CardTitle>
              </CardHeader>
              <CardBody>
                <Table responsive>
                  <thead className="text-primary">
                    <tr>
                      {customersHead.map((prop, key) => {
                        return <th key={key}>{prop}</th>;
                      })}
                    </tr>
                  </thead>
                  <tbody>
                    {customers.map((data) => {
                      return (
                        <tr key={data.id} onClick={handleCustomerEdit(data)}>
                          <td>
                            {data.firstName} {data.lastName}
                          </td>
                          <td>{data.address} kn</td>
                          <td>{data.phoneNumber}</td>
                          <td>{data.oib}</td>
                          <td>
                            <Button
                              className="btn-round btn-icon btn-icon-mini btn-neutral"
                              color="primary"
                              onClick={handleCustomerEdit(data)}
                              type="button"
                            >
                              <i className="now-ui-icons ui-2_settings-90" />
                            </Button>
                          </td>
                        </tr>
                      );
                    })}
                  </tbody>
                </Table>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    </>
  );
};

export default Customers;
