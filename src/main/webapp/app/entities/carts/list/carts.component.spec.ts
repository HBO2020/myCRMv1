import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CartsService } from '../service/carts.service';

import { CartsComponent } from './carts.component';

describe('Carts Management Component', () => {
  let comp: CartsComponent;
  let fixture: ComponentFixture<CartsComponent>;
  let service: CartsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CartsComponent],
    })
      .overrideTemplate(CartsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CartsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CartsService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.carts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
