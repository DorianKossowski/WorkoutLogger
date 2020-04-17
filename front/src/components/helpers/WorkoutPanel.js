import React, { Component } from 'react'

import './workoutPanelStyle.css';

class WorkoutPanel extends Component {

    render() {
        return (
            <div className='workoutPanelWrapper'>
                <div className='workoutPanelHeader'>
                    { this.props.data.name }
                </div>
                <div className='workoutPanelContent'>
                    <div className='workoutPanelContentFirst'>{ this.getExercises(this.props.data.exercises) }</div>
                    <div className='workoutPanelContentOthers'>Last: placeholder</div>
                    <div className='workoutPanelContentOthers'>Train</div>
                    <div className='workoutPanelContentOthers'>Stats</div>
                </div>
            </div>
        );
    }

    getExercises(exrcsObjects) {
        return Object.values(exrcsObjects).map(obj => obj.name).join(",");
    }
}

export default WorkoutPanel;