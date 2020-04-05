import GenericTableRow from './GenericTableRow';

class GenericTableHeader extends GenericTableRow {
    
    render() {
        return super.render();
    }

    getTag() {
        return 'th';
    }
}

export default GenericTableHeader;