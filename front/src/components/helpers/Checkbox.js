import React, { Component } from "react";
import { Field } from "formik";

class Checkbox extends Component {

    render() {
        return (
            <Field name={this.props.name}>
              {({ field, form }) => (
                <label className='labelStyle'>
                  <input className='checkBoxStyle'
                    type='checkbox'
                    {...this.props}
                    checked={field.value.includes(this.props.id)}
                    onChange={() => {
                        if (field.value.includes(this.props.id)) {
                            const nextValue = field.value.filter(value => value !== this.props.id);
                            form.setFieldValue(this.props.name, nextValue);
                        } else {
                            const nextValue = field.value.concat(this.props.id);
                            form.setFieldValue(this.props.name, nextValue);
                        }
                    }}
                  />
                  {this.props.value}
                </label>
              )}
            </Field>
        );
    }
}

export default Checkbox;