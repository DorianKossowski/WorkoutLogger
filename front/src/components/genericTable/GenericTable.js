import React, { Component } from 'react';
import Table from 'react-bootstrap/Table';

import GenericTableRow from './GenericTableRow';
import GenericTableHeader from './GenericTableHeader';

import './tableStyle.css';

class GenericTable extends Component {
    
    render() {
        const {header, rows, link} = this.props;
        return (
            <Table striped bordered hover variant={'dark'}>
                <thead className='thead-light'>
                    <GenericTableHeader params={header}/>
                </thead>
                <tbody>
                    {rows.map(row => <GenericTableRow key={row.id} params={row} link={link}/>)}
                </tbody>
            </Table>
        );
    }
}

export default GenericTable;