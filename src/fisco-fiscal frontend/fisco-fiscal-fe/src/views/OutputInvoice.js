import React, { useEffect, useState } from "react";

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
import { oInvoiceHead } from "variables/general";
import {
  outputInvoiceValidation,
  invoiceServiceDetailsValidation,
} from "components/Validation/ValidateValues.js";
import { useDispatch, useSelector } from "react-redux";
import { updateOutputInvoice } from "redux/actions/outputInvoiceAction";
import { createOutputInvoice } from "redux/actions/outputInvoiceAction";
import { deleteOutputInvoice } from "redux/actions/outputInvoiceAction";
import { useHistory } from "react-router-dom";

const OutputInvoice = () => {
  const [formErrors, setFromErrors] = useState({
    invoiceNumber: "",
    invoiceType: "",
    customer: "",
    serviceModel: "",
    paymentMethod: "",
    //deliveryDate: "",
  });
  const [formDetailsErrors, setFromDetailsErrors] = useState({
    serviceDescription: "",
    measureUnit: "",
    amount: "",
    price: "",
    discount: "",
    taxRate: "",
  });

  const serviceDetailsEmptyData = {
    serviceDescription: "",
    measureUnit: "",
    amount: 1,
    price: null,
    discount: 0,
    taxRate: 0,
  };

  const [optionData, setOptionData] = useState({
    customerId: 0,
    serviceId: 0,
    invoiceType: "",
    measureUnit: "",
    paymentMethod: "",
  });

  const initialOutputInvoiceDataState = {
    user: null,
    invoiceNumber: 0,
    invoiceType: "",
    customer: null,
    serviceModel: null,
    serviceDetails: [
      {
        serviceDescription: "",
        measureUnit: "",
        amount: 1,
        price: null,
        finalPrice: null,
        discount: 0,
        taxRate: 0,
      },
    ],
    paymentMethod: "",
    finalPrice: 0,
    dateAndTime: "",
    deliveryDate: "2022-07-18",
  };

  const dispatch = useDispatch();
  const history = useHistory();
  const { outputInvoiceRed, customersRed, serviceRed, auth } = useSelector(
    (state) => state
  );
  const [serviceDetailData, setServiceDetailData] = useState([]);
  const [isSubmit, setIsSubmit] = useState(false);
  const [final, setFinal] = useState(0);
  const [isEditedOutputInvoice, setIsEditedOutputInvoice] = useState(false);
  const [customers, setCustomers] = useState([]);
  const [servicesData, setServicesData] = useState([]);
  const [outputInvoices, setOutputInvoices] = useState([]);
  const [outputInvoiceData, setOutputInvoiceData] = useState(
    initialOutputInvoiceDataState
  );

  const {
    invoiceNumber,
    invoiceType,
    customer,
    serviceModel,
    serviceDetails,
    paymentMethod,
    finalPrice,
    dateAndTime,
    deliveryDate,
  } = outputInvoiceData;

  const ivoiceTypes = [
    { value: "R" },
    { value: "R1" },
    { value: "R2" },
    { value: "AVANSNI" },
    { value: "OTHER" },
  ];

  const measureUnits = [
    { value: "KOM" },
    { value: "SAT" },
    { value: "GOD" },
    { value: "KM" },
    { value: "LIT" },
    { value: "KG" },
    { value: "KWH" },
    { value: "M" },
    { value: "M2" },
    { value: "M3" },
    { value: "T" },
    { value: "G" },
    { value: "DAN" },
    { value: "MJ" },
    { value: "NOĆ" },
    { value: "PAR" },
    { value: "SOBA" },
  ];

  const paymentMethods = [
    { value: "CASH" },
    { value: "CARD" },
    { value: "CHECK" },
    { value: "OTHER" },
  ];

  useEffect(() => {

    setOutputInvoiceData({
      ...outputInvoiceData,
      user: auth.user,
    });
    setOutputInvoices(outputInvoiceRed.outputInvoices);
    setCustomers(customersRed.customers);
    setServicesData(serviceRed.services);
  }, [outputInvoiceRed.outputInvoices]);

  const handleAddDetailsSection = (e) => {
    setOutputInvoiceData({
      ...outputInvoiceData,
      serviceDetails: [...serviceDetails, serviceDetailsEmptyData],
    });
  };

  const handleViewPDF = () => {
    history.push({
      pathname: "/admin/pdf",
      state: { invoice: outputInvoiceData },
    });
  };

  const handleRootInput = (e) => {
    setOutputInvoiceData({
      ...outputInvoiceData,
      [e.target.name]:
        e.target.type === "number" ? parseInt(e.target.value) : e.target.value,
    });
  };

  const handleServiceDetailsChangeInput = (id, index) => (e) => {
    let editedObject = outputInvoiceData.serviceDetails;
    editedObject[index][e.target.name] =
      e.target.type === "number" ? parseInt(e.target.value) : e.target.value;
    setOutputInvoiceData({
      ...outputInvoiceData,
      serviceDetails: editedObject,
    });
    handleFinalPrice(index);
  };

  const handleFinalPrice = (index) => {
    let finalPrice = 0;
    let editedList = outputInvoiceData.serviceDetails;
    let editedObject = editedList[index];

    if (editedObject.price) {
      if (editedObject.taxRate > 0) {
        let tax = (editedObject.taxRate / 100) * editedObject.price;
        finalPrice = editedObject.price + tax;
      }

      if (editedObject.taxRate > 0 && editedObject.discount > 0) {
        let disc = (editedObject.discount / 100) * finalPrice;
        finalPrice = finalPrice - disc;
      }

      if (
        (!editedObject.taxRate || editedObject.taxRate <= 0) &&
        editedObject.discount > 0
      ) {
        let disc = (editedObject.discount / 100) * editedObject.price;
        finalPrice = editedObject.price - disc;
      }

      if (
        (!editedObject.taxRate && !editedObject.discount) ||
        (editedObject.taxRate <= 0 && editedObject.discount <= 0)
      ) {
        finalPrice = editedObject.price;
      }

      if (editedObject.amount > 1)
        finalPrice = finalPrice * editedObject.amount;
    }

    editedObject.finalPrice = finalPrice;
    setOutputInvoiceData({
      ...outputInvoiceData,
      serviceDetails: editedList,
      finalPrice: outputInvoiceData.serviceDetails.reduce(
        (a, v) => (a = a + v.finalPrice),
        0
      ),
    });
  };

  const handleDeleteDetailsSection = (idx) => (e) => {
    let updatedList = outputInvoiceData.serviceDetails.filter((item, index) => {
      if (index != idx) return item;
    });
    setOutputInvoiceData({
      ...outputInvoiceData,
      serviceDetails: updatedList,
      finalPrice: updatedList.reduce((a, v) => (a = a + v.finalPrice), 0),
    });
  };

  const handleChangeInput = (level) => (e) => {
    if (!level) {
      const { name, value } = e.target;
      setOutputInvoiceData({ ...outputInvoiceData, [name]: value });
    }

    if (level === "customer") {
      var assignedCustomer = { ...outputInvoiceData.customer };
      assignedCustomer = customers[e.target.value];
      setOutputInvoiceData({
        ...outputInvoiceData,
        customer: assignedCustomer,
      });
    }

    if (level === "serviceModel") {
      var assignedServiceModel = { ...outputInvoiceData.serviceModel };
      assignedServiceModel = servicesData[e.target.value];
      setOutputInvoiceData({
        ...outputInvoiceData,
        serviceModel: assignedServiceModel,
      });
    }

    console.log(outputInvoiceData);
  };

  const handleOutputInvoiceEdit = (data) => (e) => {
    console.log(data);
    setOutputInvoiceData(data);
    setIsEditedOutputInvoice(true);

    setOptionData({
      ...optionData,
      customerId: data.customer.id,
      serviceId: data.serviceModel ? data.serviceModel.id : 0,
      invoiceType: data.invoiceType,
      paymentMethod: data.paymentMethod,
    });
  };

  const handleDeleteOutputInvoice = (e) => {
    dispatch(deleteOutputInvoice(outputInvoiceData));
    setIsEditedOutputInvoice(false);
    setOptionData(initialOutputInvoiceDataState);
  };

  const handleNewOutputInvoiceAction = (e) => {
    setIsEditedOutputInvoice(!isEditedOutputInvoice);
    setOutputInvoiceData(initialOutputInvoiceDataState);

    setOptionData({
      ...optionData,
      customerId: "",
      serviceId: "",
      invoiceType: "",
      measureUnit: "",
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFromErrors(outputInvoiceValidation(outputInvoiceData));
    // setFromDetailsErrors(
    //   invoiceServiceDetailsValidation(outputInvoiceData.serviceDetails)
    // );

    //Object.keys(invoiceServiceDetailsValidation(outputInvoiceData.serviceDetails)).length === 0

    if (Object.keys(outputInvoiceValidation(outputInvoiceData)).length === 0) {
      if (isEditedOutputInvoice) {
        dispatch(updateOutputInvoice(outputInvoiceData, auth));
        setOutputInvoiceData(initialOutputInvoiceDataState);
        setIsEditedOutputInvoice(false);
        //window.location.reload(false);
      } else {
        dispatch(createOutputInvoice(outputInvoiceData));
        setOutputInvoiceData(initialOutputInvoiceDataState);
      }
    }
  };

  return (
    <>
      <PanelHeader size="sm" />

      <div className="content">
        <Row>
          <Col md="12">
            <Card>
              <CardHeader>
                <Row>
                  <Col md="12" className="center-custom">
                    <h5 className="title">Output Invoice</h5>
                    {isEditedOutputInvoice ? (
                      <>
                        <Button
                          variant="info"
                          type="button"
                          onClick={handleNewOutputInvoiceAction}
                        >
                          Add new
                        </Button>
                        <Button type="button" onClick={handleViewPDF}>
                          PDF
                        </Button>
                      </>
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
                        <label>Service</label>
                        <br></br>
                        <select
                          name="serviceModel"
                          className="dropdownSelect"
                          onChange={handleChangeInput("serviceModel")}
                        >
                          <option value="Select">Select</option>
                          {servicesData.map((service, index) => {
                            if (service.id === optionData.serviceId) {
                              return (
                                <option selected key={service.id} value={index}>
                                  {service.serviceName}
                                </option>
                              );
                            }
                            return (
                              <option key={service.id} value={index}>
                                {service.serviceName}
                              </option>
                            );
                          })}
                        </select>
                        <p className="error">{formErrors.serviceModel}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Invoice Type</label>
                        <br />
                        <select
                          className="dropdownSelect"
                          onChange={handleRootInput}
                          name="invoiceType"
                        >
                          <option value={outputInvoiceData.invoiceType}>
                            Select
                          </option>
                          {ivoiceTypes.map((invoice) => {
                            if (invoice.value === optionData.invoiceType) {
                              return (
                                <option
                                  selected
                                  key={invoice.value}
                                  value={invoice.value}
                                >
                                  {invoice.value}
                                </option>
                              );
                            }
                            return (
                              <option key={invoice.value} value={invoice.value}>
                                {invoice.value}
                              </option>
                            );
                          })}
                        </select>
                        <p className="error">{formErrors.invoiceType}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Customer</label>
                        <br></br>
                        <select
                          name="customer"
                          className="dropdownSelect"
                          onChange={handleChangeInput("customer")}
                        >
                          <option value="Select">Select</option>
                          {customers.map((customer, index) => {
                            if (customer.id === optionData.customerId) {
                              return (
                                <option
                                  selected
                                  key={customer.id}
                                  value={index}
                                >
                                  {customer.firstName} {customer.lastName}
                                </option>
                              );
                            }
                            return (
                              <option key={customer.id} value={index}>
                                {customer.firstName} {customer.lastName}
                              </option>
                            );
                          })}
                        </select>
                        <p className="error">{formErrors.customer}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Invoice Number</label>
                        <Input
                          placeholder="Invoice Number"
                          type="number"
                          value={invoiceNumber}
                          onChange={handleRootInput}
                          name="invoiceNumber"
                        />
                        <p className="error">{formErrors.invoiceNumber}</p>
                      </FormGroup>
                    </Col>
                  </Row>
                  {outputInvoiceData.serviceDetails.map((detail, index) => {
                    return (
                      <div key={index}>
                        <div className="details-section">
                          <i
                            className="now-ui-icons ui-1_simple-remove"
                            style={{
                              float: "right",
                              color: "black",
                              fontWeight: "bold",
                              fontSize: "20px",
                              cursor: "pointer",
                            }}
                            onClick={handleDeleteDetailsSection(index)}
                          />
                          <Row>
                            <Col className="pr-1" md="4">
                              <FormGroup>
                                <label>Measure Unit</label>
                                <br />
                                <select
                                  className="dropdownSelect"
                                  required
                                  onChange={handleServiceDetailsChangeInput(
                                    detail.id,
                                    index
                                  )}
                                  name="measureUnit"
                                >
                                  <option value={detail.measureUnit}>
                                    Select
                                  </option>
                                  {measureUnits.map((measureUnit) => {
                                    if (
                                      measureUnit.value === detail.measureUnit
                                    ) {
                                      return (
                                        <option
                                          selected
                                          key={measureUnit.value}
                                          value={measureUnit.value}
                                        >
                                          {measureUnit.value}
                                        </option>
                                      );
                                    }
                                    return (
                                      <option
                                        key={measureUnit.value}
                                        value={measureUnit.value}
                                      >
                                        {measureUnit.value}
                                      </option>
                                    );
                                  })}
                                </select>
                                {/* <p className="error">
                                  {formDetailsErrors.measureUnit}
                                </p> */}
                              </FormGroup>
                            </Col>
                            <Col className="pr-1" md="4">
                              <FormGroup>
                                <label>Amount</label>
                                <Input
                                  placeholder="0"
                                  min={1}
                                  value={detail.amount}
                                  onChange={handleServiceDetailsChangeInput(
                                    detail.id,
                                    index
                                  )}
                                  name="amount"
                                  type="number"
                                />
                                <p className="error">
                                  {formDetailsErrors.amount}
                                </p>
                              </FormGroup>
                            </Col>
                            <Col className="pr-1" md="4">
                              <FormGroup>
                                <label>Price</label>
                                <Input
                                  placeholder="0,00 kn"
                                  value={detail.price}
                                  onChange={handleServiceDetailsChangeInput(
                                    detail.id,
                                    index
                                  )}
                                  required
                                  min="0.00"
                                  max="999999"
                                  name="price"
                                  type="number"
                                />
                                {/* <p className="error">
                                  {formDetailsErrors.price}
                                </p> */}
                              </FormGroup>
                            </Col>
                          </Row>
                          <Row>
                            <Col className="pr-1" md="3">
                              <FormGroup>
                                <label>Tax rate %</label>
                                <Input
                                  placeholder="0%"
                                  value={
                                    detail.taxRate === null ? 0 : detail.taxRate
                                  }
                                  onChange={handleServiceDetailsChangeInput(
                                    detail.id,
                                    index
                                  )}
                                  type="number"
                                  name="taxRate"
                                />
                                {/* <p className="error">
                                  {formDetailsErrors.taxRate}
                                </p> */}
                              </FormGroup>
                            </Col>
                            <Col className="pr-1" md="3">
                              <FormGroup>
                                <label>Discount %</label>
                                <Input
                                  placeholder="0%"
                                  value={detail.discount}
                                  onChange={handleServiceDetailsChangeInput(
                                    detail.id,
                                    index
                                  )}
                                  name="discount"
                                  type="number"
                                />
                                {/* <p className="error">
                                  {formDetailsErrors.discount}
                                </p> */}
                              </FormGroup>
                            </Col>
                          </Row>
                          <Row>
                            <Row>
                              <Col className="pr-1" md="12">
                                <FormGroup>
                                  <label>Service description</label>
                                  <textarea
                                    style={{
                                      width: "100%",
                                      resize: "none",
                                      padding: "5px",
                                    }}
                                    rows="6"
                                    placeholder="Service description"
                                    value={detail.serviceDescription}
                                    onChange={handleServiceDetailsChangeInput(
                                      detail.id,
                                      index
                                    )}
                                    name="serviceDescription"
                                  />
                                  <p className="error">
                                    {formDetailsErrors.serviceDescription}
                                  </p>
                                </FormGroup>
                              </Col>
                            </Row>
                          </Row>
                        </div>
                      </div>
                    );
                  })}
                  <Row>
                    <Col md="2">
                      <Button
                        className="rounded-sm"
                        type="button"
                        onClick={handleAddDetailsSection}
                      >
                        <i className="now-ui-icons ui-1_simple-add" />
                      </Button>
                    </Col>
                  </Row>
                  <Row>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Payment method</label>
                        <br />
                        <select
                          className="dropdownSelect"
                          onChange={handleRootInput}
                          name="paymentMethod"
                        >
                          <option value={paymentMethod}>Select</option>
                          {paymentMethods.map((paymentMethod) => {
                            if (
                              paymentMethod.value === optionData.paymentMethod
                            ) {
                              return (
                                <option
                                  selected
                                  key={paymentMethod.value}
                                  value={paymentMethod.value}
                                >
                                  {paymentMethod.value}
                                </option>
                              );
                            }
                            return (
                              <option
                                key={paymentMethod.value}
                                value={paymentMethod.value}
                              >
                                {paymentMethod.value}
                              </option>
                            );
                          })}
                        </select>
                        <p className="error">{formErrors.paymentMethod}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Payment due</label>
                        <Input
                          type="date"
                          value={deliveryDate}
                          onChange={handleRootInput}
                          name="deliveryDate"
                        />
                        <p className="error">{formErrors.deliveryDate}</p>
                      </FormGroup>
                    </Col>
                    <Col className="pr-1" md="3">
                      <FormGroup>
                        <label>Final price (kn)</label>
                        <Input
                          disabled
                          type="number"
                          value={finalPrice}
                          name="finalPrice"
                        />
                      </FormGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col md="3" className="editor">
                      {isEditedOutputInvoice ? (
                        <>
                          <Button variant="primary" type="submit">
                            Update invoice
                          </Button>
                          <Button
                            variant="danger"
                            type="button"
                            onClick={handleDeleteOutputInvoice}
                          >
                            Delete
                          </Button>
                        </>
                      ) : (
                        <Button variant="info" type="submit">
                          Add new invoice
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
                <CardTitle tag="h4">Output invoices</CardTitle>
              </CardHeader>
              <CardBody>
                <Table responsive>
                  <thead className="text-primary">
                    <tr>
                      {oInvoiceHead.map((prop, key) => {
                        return <th key={key}>{prop}</th>;
                      })}
                    </tr>
                  </thead>
                  <tbody>
                    {outputInvoices.map((data) => {
                      return (
                        <tr
                          key={data.id}
                          onClick={handleOutputInvoiceEdit(data)}
                        >
                          <td>{data.invoiceNumber}</td>
                          <td>
                            {data.customer.firstName} {data.customer.lastName}
                          </td>
                          <td>{data.finalPrice} kn</td>
                          <td>{data.dateAndTime}</td>
                          <td>{data.deliveryDate}</td>
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

export default OutputInvoice;
