import _ from 'lodash';

class TrainingStateManager {
    newSetIndex = -1;

    getUpdatedExercises = (exercises, handlingSet) => {
        let exerciseToUpdate = _.find(exercises, ['id', handlingSet.exerciseId]);
        if('set' in handlingSet) {
            this.createOrUpdateSet(exerciseToUpdate, handlingSet);
         } else {
            this.removeState(exerciseToUpdate, handlingSet); 
         }
         return exercises.map( exercise => exercise.id === handlingSet.exerciseId ? exerciseToUpdate : exercise );
    }


    removeState(exerciseToUpdate, handlingSet) {
        _.remove(exerciseToUpdate.sets, ['id', handlingSet.id]);
    }

    createOrUpdateSet(exerciseToUpdate, handlingSet) {
        if ('id' in handlingSet.set) {
            const setToUpdateIndex = _.findIndex(exerciseToUpdate.sets, ['id', handlingSet.set.id]);
            exerciseToUpdate.sets.splice(setToUpdateIndex, 1, handlingSet.set);
        }
        else {
            exerciseToUpdate.sets = _.concat(exerciseToUpdate.sets, [{ id: this.newSetIndex--, ...handlingSet.set }]);
        }
    }
}

export default TrainingStateManager;