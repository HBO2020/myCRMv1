import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PayementClientService } from '../service/payement-client.service';

import { PayementClientComponent } from './payement-client.component';

describe('PayementClient Management Component', () => {
  let comp: PayementClientComponent;
  let fixture: ComponentFixture<PayementClientComponent>;
  let service: PayementClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PayementClientComponent],
    })
      .overrideTemplate(PayementClientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PayementClientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PayementClientService);

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
    expect(comp.payementClients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
