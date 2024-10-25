package com.returdev.gym_exercises_api.util;

import com.returdev.gym_exercises_api.dto.request.EquipmentRequestDTO;
import com.returdev.gym_exercises_api.dto.request.ExerciseRequestDTO;
import com.returdev.gym_exercises_api.model.enums.Muscle;
import com.returdev.gym_exercises_api.model.enums.MuscleActivationLevel;
import io.swagger.v3.oas.models.media.*;
import org.springframework.http.ProblemDetail;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class to generate Swagger schemas for various DTOs.
 */
public class SwaggerUtil {

    /**
     * Utility for creating Exercise-related Swagger schemas.
     */
    @SuppressWarnings("rawtypes")
    public static class ExerciseSchemaCreator {

        /**
         * Creates a schema for saving a new ExerciseRequestDTO.
         *
         * @return schema representing the save request DTO.
         */
        public static Schema<ExerciseRequestDTO> createSaveExerciseRequestDTOSchema() {
            Schema<ExerciseRequestDTO> schema = new Schema<>();
            schema.setProperties(createExerciseRequestDTOFields());
            return schema;
        }

        /**
         * Creates a schema for updating an existing ExerciseRequestDTO.
         *
         * @return schema representing the update request DTO with an "id" property.
         */
        public static Schema<ExerciseRequestDTO> createUpdateExerciseRequestDTOSchema() {
            Schema<ExerciseRequestDTO> schema = new Schema<>();
            schema.addProperty("id", new StringSchema().example(1));
            Map<String, Schema> fields = createExerciseRequestDTOFields();
            fields.forEach(schema::addProperty);
            return schema;
        }

        /**
         * Creates a schema for partially updating an ExerciseRequestDTO.
         *
         * @return schema representing partial update request DTO with "name" and "equipment_id" properties.
         */
        public static Schema<ExerciseRequestDTO> createPartialUpdateExerciseRequestDTOSchema() {
            Schema<ExerciseRequestDTO> schema = new Schema<>();
            schema.addProperty("name", new StringSchema().example("Sumo Squat"));
            schema.addProperty("equipment_id", new IntegerSchema().format("int64").example(2));
            return schema;
        }

        /**
         * Constructs a map of properties for ExerciseRequestDTO.
         *
         * @return map of schema fields for ExerciseRequestDTO.
         */
        private static Map<String, Schema> createExerciseRequestDTOFields() {
            LinkedHashMap<String, Schema> fields = new LinkedHashMap<>();
            fields.put("name", new StringSchema().example("Squat"));
            fields.put("description", new StringSchema().example("The squat is a fundamental strength training exercise..."));
            fields.put("equipment_id", new IntegerSchema().format("int64"));

            StringSchema muscleStringSchema = new StringSchema();
            Arrays.stream(Muscle.values()).map(Enum::name).forEach(muscleStringSchema::addEnumItem);

            StringSchema muscleActivationLevelStringSchema = new StringSchema();
            Arrays.stream(MuscleActivationLevel.values()).map(Enum::name).forEach(muscleActivationLevelStringSchema::addEnumItem);

            fields.put("muscle_engagements", new ArraySchema().items(
                    new MapSchema().addProperty("muscle", muscleStringSchema.example(Muscle.QUADRICEPS.name()))
                            .addProperty("muscle_activation_level", muscleActivationLevelStringSchema)
            ));

            return fields;
        }
    }

    /**
     * Utility for creating Equipment-related Swagger schemas.
     */
    public static class EquipmentSchemaCreator {

        /**
         * Creates a schema for saving a new EquipmentRequestDTO.
         *
         * @return schema for save request DTO.
         */
        public static Schema<EquipmentRequestDTO> createSaveEquipmentRequestDTOSchema() {
            Schema<EquipmentRequestDTO> schema = new Schema<>();
            schema.addProperty("name", new StringSchema().example("Dumbbells"));
            return schema;
        }

        /**
         * Creates a schema for updating an existing EquipmentRequestDTO.
         *
         * @return schema with "id" and "name" properties.
         */
        public static Schema<EquipmentRequestDTO> createUpdateEquipmentRequestDTOSchema() {
            Schema<EquipmentRequestDTO> schema = new Schema<>();
            schema.addProperty("id", new IntegerSchema().format("int64").example("1"));
            schema.addProperty("name", new StringSchema().example("Dumbbells"));
            return schema;
        }
    }

    /**
     * Utility for creating error-related Swagger schemas.
     */
    public static class ErrorSchemaCreator {

        /**
         * Creates a basic schema for ProblemDetail.
         *
         * @return schema with standard error properties.
         */
        public static Schema<ProblemDetail> createProblemDetailSchema() {
            Schema<ProblemDetail> schema = new Schema<>();
            schema.addProperty("type", new StringSchema());
            schema.addProperty("title", new StringSchema());
            schema.addProperty("status", new IntegerSchema());
            schema.addProperty("detail", new StringSchema());
            schema.addProperty("instance", new StringSchema());
            return schema;
        }

        /**
         * Creates a schema for a bad request error with additional error messages.
         *
         * @return schema for bad request errors including an "errors" property.
         */
        public static Schema<ProblemDetail> createBadRequestProblemDetailSchema() {
            Schema<ProblemDetail> schema = createProblemDetailSchema();
            schema.addProperty("errors", new MapSchema()
                    .addProperty("errorMsg1", new StringSchema())
                    .addProperty("errorMsg2", new StringSchema())
                    .addProperty("errorMsg3", new StringSchema())
            );
            return schema;
        }
    }
}

