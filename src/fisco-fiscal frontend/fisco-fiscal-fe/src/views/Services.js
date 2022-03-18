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

import { servicesHead } from "variables/general";
import { servicesValidation } from "components/Validation/ValidateValues.js";
import { useDispatch, useSelector } from "react-redux";
import { updateService } from "redux/actions/serviceAction";
import { createService } from "redux/actions/serviceAction";
import { deleteService } from "redux/actions/serviceAction";

const Services = () => {
  const { serviceRed, auth } = useSelector((state) => state);
  const initialServiceData = {
    user: auth.user,
    serviceNumber: 0,
    serviceName: "",
  };

  const dispatch = useDispatch();
  const [isSubmit, setIsSubmit] = useState(false);
  const [isEditedService, setIsEditedService] = useState(false);
  const [services, setServices] = useState([]);
  const [serviceData, setServiceData] = useState(initialServiceData);
  const [formErrors, setFromErrors] = useState({
    serviceNumber: "",
    serviceName: "",
  });

  const { serviceNumber, serviceName } = serviceData;

  useEffect(() => {
    console.log(serviceRed.services)
    setServices(serviceRed.services);
  }, [serviceRed.services]);

  const handleChangeInput = (e) => {
    const { name, value } = e.target;
    setServiceData({ ...serviceData, [name]: value });
    console.log(serviceData);
  };

  const handleServiceEdit = (data) => (e) => {
    setServiceData(data);
    setIsEditedService(true);
  };

  const handleDeleteService = (e) => {
    dispatch(deleteService(serviceData));
    setIsEditedService(false);
    setServiceData(initialServiceData);
  };

  const handleNewServiceAction = (e) => {
    setIsEditedService(!isEditedService);
    setServiceData(initialServiceData);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFromErrors(servicesValidation(serviceData));
    setIsSubmit(true);

    if (Object.keys(servicesValidation(serviceData)).length === 0) {
      if (isEditedService) {
        dispatch(updateService(serviceData));
        setServiceData(initialServiceData);
        setIsEditedService(false);
      } else {
        dispatch(createService(serviceData));
        setServiceData(initialServiceData);
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
                    <h5 className="title">Add Service</h5>
                    {isEditedService ? (
                      <Button
                        variant="info"
                        type="button"
                        onClick={handleNewServiceAction}
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
                        <label>Service number</label>
                        <Input
                          placeholder="Service number"
                          type="number"
                          value={serviceNumber}
                          onChange={handleChangeInput}
                          name="serviceNumber"
                        />
                        <p className="error">{formErrors.serviceNumber}</p>
                      </FormGroup>
                    </Col>
                    <Col className="px-1" md="3">
                      <FormGroup>
                        <label>Service name</label>
                        <Input
                          placeholder="Service name..."
                          type="text"
                          onChange={handleChangeInput}
                          value={serviceName}
                          name="serviceName"
                        />
                        <p className="error">{formErrors.serviceName}</p>
                      </FormGroup>
                    </Col>
                  </Row>

                  <Row>
                    <Col md="3" className="editor">
                      {isEditedService ? (
                        <>
                          <Button variant="primary" type="submit">
                            Update service
                          </Button>
                          <Button
                            variant="danger"
                            type="button"
                            onClick={handleDeleteService}
                          >
                            Delete
                          </Button>
                        </>
                      ) : (
                        <Button variant="info" type="submit">
                          Add new service
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
                <CardTitle tag="h4">Usluge</CardTitle>
              </CardHeader>
              <CardBody>
                <Table responsive>
                  <thead className="text-primary">
                    <tr>
                      {servicesHead.map((prop, key) => {
                        return <th key={key}>{prop}</th>;
                      })}
                    </tr>
                  </thead>
                  <tbody>
                    {services.map((data) => {
                      return (
                        <tr key={data.id} onClick={handleServiceEdit(data)}>
                          <td>{data.serviceNumber}</td>
                          <td>{data.serviceName}</td>
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

export default Services;
