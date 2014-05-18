package fr.letzner.graphics.actors;

public class Camera {
	
	private float angle = 0.0f;
	private float eye_x = 0.0f;
	private float eye_y = 0.0f;
	private float eye_z = 0.0f;
	private float at_x = 0.0f;
	private float at_y = 0.0f;
	private float at_z = 0.0f;
	private float up_x = 0.0f;
	private float up_y = 1.0f;
	private float up_z = 0.0f;
	private float offset = 5.0f;
	private float distance = 20.0f;
	
	
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public float getEye_x() {
		return eye_x;
	}
	public void setEye_x(float eye_x) {
		this.eye_x = eye_x;
	}
	public float getEye_y() {
		return eye_y;
	}
	public void setEye_y(float eye_y) {
		this.eye_y = eye_y;
	}
	public float getEye_z() {
		return eye_z;
	}
	public void setEye_z(float eye_z) {
		this.eye_z = eye_z;
	}
	public float getAt_x() {
		return at_x;
	}
	public void setAt_x(float at_x) {
		this.at_x = at_x;
	}
	public float getAt_y() {
		return at_y;
	}
	public void setAt_y(float at_y) {
		this.at_y = at_y;
	}
	public float getAt_z() {
		return at_z;
	}
	public void setAt_z(float at_z) {
		this.at_z = at_z;
	}
	public float getUp_x() {
		return up_x;
	}
	public void setUp_x(float up_x) {
		this.up_x = up_x;
	}
	public float getUp_y() {
		return up_y;
	}
	public void setUp_y(float up_y) {
		this.up_y = up_y;
	}
	public float getUp_z() {
		return up_z;
	}
	public void setUp_z(float up_z) {
		this.up_z = up_z;
	}
	public float getOffset() {
		return offset;
	}
	public void setOffset(float offset) {
		this.offset = offset;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public String toString(){
		return "Eye : " + eye_x + ", " + eye_y + ", " + eye_z + " At : " + at_x + ", " + at_y + ", " + at_z;
	}

}
