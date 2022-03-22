import React, { Component, useEffect, useRef, useState } from "react";

// core components
import "assets/css/fisco-pdf.css";

import NotificationAlert from "react-notification-alert";
import PanelHeader from "components/PanelHeader/PanelHeader.js";
import Button from "react-bootstrap/Button";
import { useDispatch, useSelector } from "react-redux";
import { updateCompany } from "redux/actions/companyAction";
import notify from "variables/notify";
import img from "../assets/img/Blic_Main_Logo.png";
import ReactToPrint from "react-to-print";
import { Card, CardBody, CardHeader, Col, Row, Table } from "reactstrap";
import { useHistory } from "react-router-dom";

const PDFFileView = (props) => {

  const componentRef = useRef();
  const history = useHistory();

  const checkForProps = () => {
    if(!props.location.state){
      history.push("/admin/output-invoice");
    }
  }


  return (
    <>
      {checkForProps()}
      <PanelHeader size="sm" />
      <div>
        <div className="content" ref={componentRef}>
          <Row>
            <Col md={12}>
              <Card>
                <CardBody className="all-icons">
                  <div>
                    <div className="pdfHeader">
                      <img src={img} />
                      <h2>Račun br. 66555</h2>
                    </div>

                    <hr />

                    <div className="pdfDetails">
                      <div className="sender">
                        <p className="bold">BLIC Servis d.o.o</p>
                        <p>Grigora Viteza 1C, 10000 Zadar</p>
                        <p className="bold">OIB: 27377759379</p>
                      </div>
                      <div className="reciever">
                        <p className="bold">Batimat d.o.o</p>
                        <p>Ražanac, 102142 asd</p>
                        <p className="bold">OIB: 27377759379</p>
                      </div>
                    </div>

                    <hr />

                    <div className="pdfBody">
                      <Table responsive style={{ backgroundColor: "#d9d9d9" }}>
                        <thead>
                          <tr>
                            <th>R.br.</th>
                            <th>Opis proizvoda/usluge</th>
                            <th>Jed.</th>
                            <th>Kol.</th>
                            <th>Iznos stavke</th>
                            <th>Cijena</th>
                          </tr>
                        </thead>
                        <tbody>
                          {props.location.state.invoice.serviceDetails.map(
                            (item, index) => {
                              return (
                                <tr key={index}>
                                  <td>{index + 1}</td>
                                  <td>{item.serviceDescription}</td>
                                  <td>{item.measureUnit}</td>
                                  <td>{item.amount}</td>
                                  <td>{item.price}</td>
                                  <td>{item.finalPrice}</td>
                                </tr>
                              );
                            }
                          )}
                        </tbody>
                      </Table>
                    </div>

                    <div className="pdfDetails">
                      <div>
                        <p>
                          <strong>Način plaćanja:</strong>{" "}
                          {props.location.state.invoice.paymentMethod}
                        </p>
                      </div>
                      <div className="pdfFinalPrice">
                        <h5>
                          Ukupan iznos računa:{" "}
                          {props.location.state.invoice.finalPrice} kn
                        </h5>
                      </div>
                    </div>

                    <div style={{ marginTop: "20px" }}>
                      <p>
                        <strong>Datum računa:</strong>{" "}
                        {props.location.state.invoice.dateAndTime}
                      </p>
                      <p>
                        <strong>Rok plaćanja:</strong>{" "}
                        {props.location.state.invoice.deliveryDate}
                      </p>
                    </div>
                  </div>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </div>
        <ReactToPrint
          trigger={() => {
            return <Button type="button">PRINT</Button>;
          }}
          content={() => componentRef.current}
        />
      </div>
    </>
  );

  // function MyDocument() {
  //   return (
  //     <Row>
  //       <Col md="3">Marko</Col>
  //       <Col md="3">Marko 1</Col>
  //       <Col md="3">Marko 2</Col>
  //     </Row>
  //   );
  // }
};

export default PDFFileView;
