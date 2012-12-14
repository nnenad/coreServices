/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.domain.access;

import com.java.web.core.broker.AbstractBroker;
import com.java.web.core.config.managers.MainConfigManager;
import com.java.web.core.lib.json.JSONObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.java.web.core.lib.date.DateTime;
import com.java.web.core.lib.encodeings.Encoder;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vuk
 */
public class BaseEntity implements java.io.Serializable {

    protected String idColumnName;
    protected String className;
    protected ArrayList<String> transientFieldNames;
    protected ArrayList<String> restrictedFieldNames;
    protected boolean hasGlobalFields;
    protected ArrayList<String> globalFields = new ArrayList<String>();
    protected boolean hasInheritedFields;

    public void fromJSON(JSONObject jsonSourceObject) throws Exception {
        Class c = this.getClass();
        Class superClass = c.getSuperclass();
        for (Field field : c.getDeclaredFields()) {
            String fieldName = field.getName();
            this.populateFieldValues(jsonSourceObject, c, field, fieldName);
        }

        if (this.hasGlobalFields) {
            for (Field field : superClass.getDeclaredFields()) {
                String fieldName = field.getName();
                this.populateFieldValues(jsonSourceObject, superClass, field, fieldName);
            }
            populateGlobalFields(c);
        }
        if (this.hasInheritedFields) {
            for (Field field : superClass.getDeclaredFields()) {
                String fieldName = field.getName();
                this.populateFieldValues(jsonSourceObject, superClass, field, fieldName);
            }
        }
    }

    public void setDefaultFieldValues() {
    }

    protected void populateGlobalFields(Class c) throws Exception {
    }

    private void populateCharacterField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Character.class},
                    new Object[]{new java.lang.Character(Encoder.encodeToLatin(object.getString(fieldName)).charAt(0))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Character.class},
                    new Object[]{new java.lang.Character(' ')});
        }
    }

    private void populateCharField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Character.class},
                    new Object[]{new java.lang.Character(Encoder.encodeToLatin(object.getString(fieldName)).charAt(0))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{char.class},
                    new Object[]{new java.lang.Character(' ')});
        }
    }

    private void populateByteField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Byte.class},
                    new Object[]{new java.lang.Byte(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Byte.class},
                    new Object[]{new java.lang.Byte("0")});
        }
    }

    private void populateBytePrimitiveField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{byte.class},
                    new Object[]{new java.lang.Byte(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{byte.class},
                    new Object[]{new java.lang.Byte("0")});
        }
    }

    private void populateShortField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Short.class},
                    new Object[]{new java.lang.Short(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Short.class},
                    new Object[]{new java.lang.Short("0")});
        }
    }

    private void populateShortPrimitiveField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{short.class},
                    new Object[]{new java.lang.Short(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{short.class},
                    new Object[]{new java.lang.Short("0")});
        }
    }

    private void populateIntegerField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Integer.class},
                    new Object[]{new java.lang.Integer(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Integer.class},
                    new Object[]{new java.lang.Integer("0")});
        }
    }

    private void populateIntField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{int.class},
                    new Object[]{new java.lang.Integer(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{int.class},
                    new Object[]{new java.lang.Integer("0")});
        }
    }

    private void populateLongField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Long.class},
                    new Object[]{new java.lang.Long(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Long.class},
                    new Object[]{new java.lang.Long("0")});
        }
    }

    private void populateLongPrimitiveField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{long.class},
                    new Object[]{new java.lang.Long(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{long.class},
                    new Object[]{new java.lang.Long("0")});
        }
    }

    private void populateFloatField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Float.class},
                    new Object[]{new java.lang.Float(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Float.class},
                    new Object[]{new java.lang.Float("0")});
        }
    }

    private void populateFloatPrimitiveField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{float.class},
                    new Object[]{new java.lang.Float(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{float.class},
                    new Object[]{new java.lang.Float("0")});
        }
    }

    private void populateDouble(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Double.class},
                    new Object[]{new java.lang.Double(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Double.class},
                    new Object[]{new java.lang.Double("0")});
        }
    }

    private void populateDoublePrimitiveField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{double.class},
                    new Object[]{new java.lang.Double(object.getString(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{double.class},
                    new Object[]{new java.lang.Double("0")});
        }
    }

    private void populateBoolean(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Boolean.class},
                    new Object[]{new java.lang.Boolean(object.getBoolean(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.Boolean.class},
                    new Object[]{null});
        }
    }

    private void populateBooleanPrimitiveField(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{boolean.class},
                    new Object[]{new java.lang.Boolean(object.getBoolean(fieldName))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{boolean.class},
                    new Object[]{null});
        }
    }

    private void populateString(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty()) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.String.class},
                    new Object[]{new java.lang.String(Encoder.encodeToLatin(object.getString(fieldName)))});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.lang.String.class},
                    new Object[]{new java.lang.String("")});
        }
    }

    private void populateDate(Class c, JSONObject object, String fieldName) throws Exception {
        if (object.getString(fieldName) != null && !object.getString(fieldName).isEmpty() && !object.getString(fieldName).equals("null")) {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.util.Date.class},
                    new Object[]{DateTime.fromYMDString(object.getString(fieldName)).toDate()});
        } else {
            this.invoke(this, c, this.getfieldSetterName(fieldName),
                    new Class[]{java.util.Date.class},
                    new Object[]{null});
        }
    }

    private void populateFirstPart(JSONObject jsonSourceObject, Class c, Field field, String fieldName) throws Exception {
        if (field.getType().getName().equals("java.lang.Character")) {
            this.populateCharacterField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("char")) {
            this.populateCharField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("java.lang.Byte")) {
            this.populateByteField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("byte")) {
            this.populateBytePrimitiveField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("java.lang.Short")) {
            this.populateShortField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("short")) {
            this.populateShortPrimitiveField(c, jsonSourceObject, fieldName);
        }
    }

    private void populateSecondPart(JSONObject jsonSourceObject, Class c, Field field, String fieldName) throws Exception {
        if (field.getType().getName().equals("java.lang.Integer")) {
            this.populateIntegerField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("int")) {
            this.populateIntField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("java.lang.Long")) {
            this.populateLongField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("long")) {
            this.populateLongPrimitiveField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("java.lang.Float")) {
            this.populateFloatField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("float")) {
            this.populateFloatPrimitiveField(c, jsonSourceObject, fieldName);
        }
    }

    private void populateThirdPart(JSONObject jsonSourceObject, Class c, Field field, String fieldName) throws Exception {
        if (field.getType().getName().equals("java.lang.Double")) {
            this.populateDouble(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("double")) {
            this.populateDoublePrimitiveField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("java.lang.Boolean")) {
            this.populateBoolean(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("boolean")) {
            this.populateBooleanPrimitiveField(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("java.lang.String")) {
            this.populateString(c, jsonSourceObject, fieldName);
        }
        if (field.getType().getName().equals("java.util.Date")) {
            this.populateDate(c, jsonSourceObject, fieldName);
        }
    }

    private void populateFieldValues(JSONObject jsonSourceObject, Class c, Field field, String fieldName) throws Exception {
        boolean hasKey = jsonSourceObject.has(fieldName);
        if (hasKey) {
            if (BaseEntity.class.isAssignableFrom(field.getType())) {
                BaseEntity entity = (BaseEntity) field.getType().newInstance();
                entity = MainConfigManager.getBaseBroker().findById(entity.getClassName(),
                        new Integer(jsonSourceObject.getString(fieldName)));
                if (entity != null) {
                    this.invoke(this, c, this.getfieldSetterName(fieldName), new Class[]{entity.getClass()}, new Object[]{entity});
                }
            }
            this.populateFirstPart(jsonSourceObject, c, field, fieldName);
            this.populateSecondPart(jsonSourceObject, c, field, fieldName);
            this.populateThirdPart(jsonSourceObject, c, field, fieldName);
        }
    }

    public void toJSONObject(JSONObject object) throws Exception {
        Class c = this.getClass();
        Class superClass = c.getSuperclass();
        if (this.hasGlobalFields) {
            for (Field field : superClass.getDeclaredFields()) {
                String fieldName = field.getName();
                if (!isRestrictedField(field)) {
                    this.populateJsonField(superClass, object, field, fieldName);
                }
            }
        }
        if (this.hasInheritedFields) {
            for (Field field : superClass.getDeclaredFields()) {
                String fieldName = field.getName();
                if (!isRestrictedField(field)) {
                    this.populateJsonField(superClass, object, field, fieldName);
                }
            }
        }
        for (Field field : c.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!isRestrictedField(field)) {
                this.populateJsonField(c, object, field, fieldName);
            }
        }
    }

    public void toJSONObject(JSONObject object, ArrayList<String> selectedFieldNames) throws Exception {
        Class c = this.getClass();
        for (Field field : c.getDeclaredFields()) {
            String fieldName = field.getName();
            if (selectedFieldNames.contains(fieldName)) {
                this.populateJsonField(c, object, field, fieldName);
            }
        }
    }

    private boolean isRestrictedField(Field field) {
        List<String> restrictedFields = this.getRestrictedFieldNames();
        if (restrictedFields != null && !restrictedFields.isEmpty()) {
            for (String restrictedFieldName : restrictedFields) {
                if (restrictedFieldName.equals(field.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected String getfieldGetterName(String fieldName) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String theRestOfTheLetters = fieldName.substring(1);
        String getterPrefix = "get";
        return getterPrefix + firstLetter + theRestOfTheLetters;
    }

    protected String getfieldSetterName(String fieldName) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String theRestOfTheLetters = fieldName.substring(1);
        String setterPrefix = "set";
        return setterPrefix + firstLetter + theRestOfTheLetters;
    }

    protected Object invoke(BaseEntity instance, Class c, String methodName, Class[] params, Object[] args) throws Exception {
        Method m = c.getDeclaredMethod(methodName, params);
        Object r = m.invoke(instance, args);
        return r;
    }

    private void populateJsonFieldFromChar(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Character value = (Character) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.accumulate(fieldName, Encoder.encodeToUtf8(value.toString()));
        } else {
            object.accumulate(fieldName, (new java.lang.Character(' ')).toString());
        }
    }

    private void populateJsonFieldFromByte(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Byte value = (Byte) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, value.byteValue());
        } else {
            object.put(fieldName, (byte) 0);
        }
    }

    private void populateJsonFieldFromShort(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Short value = (Short) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, value.shortValue());
        } else {
            object.put(fieldName, (short) 0);
        }
    }

    private void populateJsonFieldFromInt(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Integer value = (Integer) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, value.intValue());
        } else {
            object.put(fieldName, (int) 0);
        }
    }

    private void populateJsonFieldFromLong(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Long value = (Long) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, value.longValue());
        } else {
            object.put(fieldName, (long) 0);
        }
    }

    private void populateJsonFieldFromFloat(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Float value = (Float) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, value.doubleValue());
        } else {
            object.put(fieldName, (float) 0);
        }
    }

    private void populateJsonFieldFromDouble(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Double value = (Double) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, value.doubleValue());
        } else {
            object.put(fieldName, (double) 0);
        }
    }

    private void populateJsonFieldFromBool(Class c, String fieldName, JSONObject object) throws Exception {
        java.lang.Boolean value = (Boolean) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, value.booleanValue());
        } else {
            object.put(fieldName, false);
        }
    }

    private void populateJsonFieldFromString(Class c, String fieldName, JSONObject object) throws Exception {
        String trimName = fieldName;
        if (trimName.contains("_")) {
            int index = fieldName.indexOf("_");
            trimName = fieldName.substring(index + 1);
        }
        java.lang.String value = (String) this.invoke(this, c, this.getfieldGetterName(trimName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, Encoder.encodeToUtf8(value));
        } else {
            object.put(fieldName, "");
        }
    }

    private void populateJsonFieldFromDate(Class c, String fieldName, JSONObject object) throws Exception {
        java.util.Date value = (Date) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
        if (value != null) {
            object.put(fieldName, (DateTime.fromDate(value)).toString());
        } else {
            object.put(fieldName, "");
        }
    }

    private void populateJsonFieldFirst(Class c, JSONObject object, Field field, String fieldName) throws Exception {
        if (field.getType().getName().equals("java.lang.Character") || field.getType().getName().equals("char")) {
            this.populateJsonFieldFromChar(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.lang.Byte") || field.getType().getName().equals("byte")) {
            this.populateJsonFieldFromByte(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.lang.Short") || field.getType().getName().equals("short")) {
            this.populateJsonFieldFromShort(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.lang.Integer") || field.getType().getName().equals("int")) {
            this.populateJsonFieldFromInt(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.lang.Long") || field.getType().getName().equals("long")) {
            this.populateJsonFieldFromLong(c, fieldName, object);
        }
    }

    private void populateJsonFieldSecond(Class c, JSONObject object, Field field, String fieldName) throws Exception {
        if (field.getType().getName().equals("java.lang.Float") || field.getType().getName().equals("float")) {
            this.populateJsonFieldFromFloat(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.lang.Double") || field.getType().getName().equals("double")) {
            this.populateJsonFieldFromDouble(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.lang.Boolean") || field.getType().getName().equals("boolean")) {
            this.populateJsonFieldFromBool(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.lang.String")) {
            this.populateJsonFieldFromString(c, fieldName, object);
        }
        if (field.getType().getName().equals("java.util.Date")) {
            this.populateJsonFieldFromDate(c, fieldName, object);
        }
    }

    private void populateJsonField(Class c, JSONObject object, Field field, String fieldName) throws Exception {
        if (BaseEntity.class.isAssignableFrom(field.getType())) {
            BaseEntity entity = (BaseEntity) this.invoke(this, c, this.getfieldGetterName(fieldName), new Class[]{}, new Object[]{});
            if (entity != null) {
                Class classOfEntity = entity.getClass();
                String fieldIdName = entity.getIdColumnName();
                Field idField = classOfEntity.getDeclaredField(fieldIdName);
                java.lang.Integer value = (java.lang.Integer) this.invoke(entity, classOfEntity, this.getfieldGetterName(idField.getName()),
                        new Class[]{}, new Object[]{});
                if (value != null) {
                    object.put(fieldName, value.intValue());
                } else {
                    object.put(fieldName, "");
                }
                if (entity.getTransientFieldNames() != null && !entity.getTransientFieldNames().isEmpty()) {
                    for (String transientFieldName : entity.getTransientFieldNames()) {
                        Field transientField = entity.getClass().getDeclaredField(transientFieldName);
                        entity.populateJsonField(classOfEntity, object, transientField, fieldName + "_" + transientFieldName);
                    }
                }
            }
        }
        this.populateJsonFieldFirst(c, object, field, fieldName);
        this.populateJsonFieldSecond(c, object, field, fieldName);
    }

    /**
     * @return the idColumnName
     */
    public String getIdColumnName() {
        return idColumnName;
    }

    /**
     * @param idColumnName the idColumnName to set
     */
    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the transientFieldNames
     */
    public ArrayList<String> getTransientFieldNames() {
        return transientFieldNames;
    }

    /**
     * @param transientFieldNames the transientFieldNames to set
     */
    public void setTransientFieldNames(ArrayList<String> transientFieldNames) {
        this.transientFieldNames = transientFieldNames;
    }

    public ArrayList<String> getRestrictedFieldNames() {
        return restrictedFieldNames;
    }

    public void setRestrictedFieldNames(ArrayList<String> restrictedFieldNames) {
        this.restrictedFieldNames = restrictedFieldNames;
    }
}
